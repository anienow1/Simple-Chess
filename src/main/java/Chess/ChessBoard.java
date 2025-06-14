package Chess;

import java.util.ArrayList;
import java.util.Stack;

import Chess.Pieces.Bishop;
import Chess.Pieces.King;
import Chess.Pieces.Knight;
import Chess.Pieces.Pawn;
import Chess.Pieces.Piece;
import Chess.Pieces.Queen;
import Chess.Pieces.Rook;
import javafx.scene.paint.Color;

/**
 * Represents the main logic and state of the chessboard.
 * Handles piece initialization, move validation, check detection, and game
 * state.
 */

public class ChessBoard {

    // 8 x 8 array representing the board squares
    private GameSquare[][] board = new GameSquare[8][8];
    private Main mainApp;

    // Size of each gride pixel, determining by the window height.
    private final double SQUARE_SIZE;
    private boolean isWhiteTurn = true;

    public GameSquare selectedSquare;

    private boolean wait = false;
    private GameSquare pendingPromotionSquare = null;

    // Refrences to the current tiles holding the two kings.
    private GameSquare whiteKingSquare;
    private GameSquare blackKingSquare;

    private GameSquare whiteEnPassantTarget = null;
    private GameSquare blackEnPassantTarget = null;

    private final Stack<Move> moveHistory = new Stack<>();

    /**
     * Constructs the chessboard and initializes all pieces.
     * 
     * @param windowHeight The height of the application window.
     */
    public ChessBoard(double windowHeight, Main main) {
        mainApp = main;
        SQUARE_SIZE = windowHeight / 8;
        initializeNewBoard();
    }

    /**
     * Set up a full chess board of GameSquare objects with pieces in their starting
     * positions.
     */
    private void initializeNewBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                // Checkerboard color pattern.
                Color color;
                if ((row + col) % 2 == 0) {
                    color = Color.rgb(238, 237, 210); // Light square
                } else {
                    color = Color.rgb(117, 148, 91); // Dark Square
                }

                if (row == 1 || row == 6) { // Pawns
                    board[row][col] = new GameSquare(row, col, new Pawn(row == 1 ? false : true, row, col), this,
                            color);
                } else if (row != 0 && row != 7) { // Empty
                    board[row][col] = new GameSquare(row, col, null, this, color);
                } else {
                    Piece piece;
                    switch (col) {
                        case (0):
                        case (7):
                            piece = new Rook(row == 7, row, col);
                            break;
                        case (1):
                        case (6):
                            piece = new Knight(row == 7, row, col);
                            break;
                        case (2):
                        case (5):
                            piece = new Bishop(row == 7, row, col);
                            break;
                        case (3):
                            piece = new Queen(row == 7, row, col);
                            break;
                        case (4):
                            piece = new King(row == 7, row, col);
                            break;
                        default:
                            piece = null;
                    }
                    board[row][col] = new GameSquare(row, col, piece, this, color);
                    if (piece.getName().equals("King")) {
                        if (row == 0) {
                            blackKingSquare = board[row][col];
                        } else {
                            whiteKingSquare = board[row][col];
                        }
                    }
                }
            }
        }
    }

    /**
     * @return The 8x8 array of GamesSquare objects represnting the board.
     */
    public GameSquare[][] getBoard() {
        return board;
    }

    /**
     * @return The Pixel size of each GameSquare
     */
    public double getSquareSize() {
        return SQUARE_SIZE;
    }

    public GameSquare getEnPassantSquare(boolean isWhite) {
        if (isWhite)
            return whiteEnPassantTarget;
        return blackEnPassantTarget;
    }

    /**
     * @return The current (if any) selected square on the board.
     */
    public GameSquare getSelectedSquare() {
        return selectedSquare;
    }

    /**
     * @return The current turn. True = White, False = Black.
     */
    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    /**
     * Switches the turn between white and black.
     */
    public void changeTurns() {
        isWhiteTurn = !isWhiteTurn;
    }

    public Stack<Move> getHistory() {
        return moveHistory;
    }

    /**
     * Sets the currently selected square.
     * 
     * @param square The GameSquare to be selected.
     */
    public void setSelectedSquare(GameSquare square) {
        selectedSquare = square;
    }

    /**
     * Converts between the GameSquare.SquareColor enum and isWhite boolean.
     * 
     * @param squareColor
     * @param isWhite
     * @return True if the two colors match, false otherwise.
     */
    public boolean colorMatches(GameSquare.SquareColor squareColor, boolean isWhite) {
        if (isWhite && squareColor == GameSquare.SquareColor.WHITE ||
                !isWhite && squareColor == GameSquare.SquareColor.BLACK) {
            return true;
        }
        return false;
    }

    /**
     * Method for highlighting all of the GameSquares after the board is clicked.
     * All square highlights are cleared, the clicked square is highlighted,
     * and all valid moves for the selected piece become circles.
     * 
     * @param square The clicked square.
     */
    public void squareClicked(GameSquare square) { // Remove all markings from all board squares
        if (wait)
            return;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                this.getBoard()[row][col].isClicked(false);
            }
        }

        square.isClicked(true); // Highlight the input square and find all moves for that piece.

        if (square.getPiece() != null && colorMatches(square.getPieceColor(), isWhiteTurn)) {
            ArrayList<GameSquare> squares = this.findPossibleMoves(square.getPiece());
            markValidSquare(squares); // Take the valid square list and apply circles on the board.
        }
    }

    /**
     * Checks if the king of the specified color is in check.
     * 
     * @param kingColorIsWhite The color of the king. True = white, false = black.
     * @return True if the king is in check, false if it is not.
     */
    public boolean isKingInCheck(boolean kingColorIsWhite) {
        GameSquare kingSquare = kingColorIsWhite ? whiteKingSquare : blackKingSquare;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                GameSquare otherSquare = this.board[row][col];

                if (!otherSquare.isEmpty() && otherSquare.getPieceColor() != kingSquare.getPieceColor()) {
                    if (otherSquare.getPiece().canMove(this, otherSquare, kingSquare)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Simulates moving a piece between two squares, checks if the player's king
     * would be in check after the move, then reverts the move.
     * 
     * @param aPiece The piece to move.
     * @param start  The starting square.
     * @param end    The ending square.
     * @return true if the move leaves the king in check.
     */
    public boolean moveLeavesKingInCheck(Piece aPiece, GameSquare start, GameSquare end) {
        Piece capturedPiece = end.getPiece();

        // Make the move
        end.setPiece(aPiece);
        start.removePiece();

        // Save old king square if moving king
        GameSquare oldKingSquare = null;
        if (aPiece instanceof King) {
            oldKingSquare = aPiece.isWhite() ? whiteKingSquare : blackKingSquare;
            if (aPiece.isWhite())
                whiteKingSquare = end;
            else
                blackKingSquare = end;
        }

        // Check if king is in check after move
        boolean kingInCheck = isKingInCheck(aPiece.isWhite());

        // Undo the move
        start.setPiece(aPiece);
        if (capturedPiece != null) {
            end.setPiece(capturedPiece);
        } else {
            end.removePiece();
        }

        // Restore king position
        if (oldKingSquare != null) {
            if (aPiece.isWhite())
                whiteKingSquare = oldKingSquare;
            else
                blackKingSquare = oldKingSquare;
        }

        return kingInCheck;
    }

    /**
     * Checks if the player of the specified color is in checkmate.
     * Checkmate is met if the king is in check, and has no moves to get out of it.
     * 
     * @param isWhite The color of the player. True = white, false = black.
     * @return True if in checkmate, false if not.
     */
    public boolean hasAnyLegalMove(boolean isWhite) {

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                GameSquare startSquare = board[row][col];

                if (startSquare.isEmpty() || !colorMatches(startSquare.getPieceColor(), isWhite)) {
                    continue;
                }

                Piece piece = startSquare.getPiece();

                for (int moveRow = 0; moveRow < 8; moveRow++) {
                    for (int moveCol = 0; moveCol < 8; moveCol++) {
                        GameSquare moveTo = board[moveRow][moveCol];

                        if (piece.canMove(this, startSquare, moveTo)) {
                            if (!moveLeavesKingInCheck(piece, startSquare, moveTo)) {
                                return true;
                            }
                        }

                    }
                }
            }
        }
        return false;
    }

    private void updateKingSquare(Piece piece, GameSquare start, GameSquare end) {
        if (piece instanceof King) {
            if (piece.isWhite()) {
                whiteKingSquare = end;
                start.setInCheck(false);
            } else {
                blackKingSquare = end;
                start.setInCheck(false);
            }
        }
    }
    
    /**
     * Searches for and returns all valid moves for the input piece to make on the
     * game board. This method filters out all moves that leave the king in check.
     * 
     * @param aPiece Chess piece to find the valid moves for.
     * @return An ArrayList of all legal moves for the input piece to make.
     */
    private ArrayList<GameSquare> findPossibleMoves(Piece aPiece) {
        ArrayList<GameSquare> possibleMoves = new ArrayList<>();
        GameSquare start = board[aPiece.getRow()][aPiece.getCol()];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                GameSquare end = board[row][col];
                if (aPiece.canMove(this, start, end)) { // Requires start
                    if (!moveLeavesKingInCheck(aPiece, start, end)) {
                        possibleMoves.add(board[row][col]);
                    }
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Highlights all squares that are valid for a piece to make.
     * 
     * @param possibleMoves List of the squares to highlight.
     */
    private void markValidSquare(ArrayList<GameSquare> possibleMoves) {
        for (GameSquare square : possibleMoves) {
            square.placeCircle();
        }
    }

    public void finalizePromotion(Piece promotedPiece) {
        if (pendingPromotionSquare != null) {
            pendingPromotionSquare.setPiece(promotedPiece);
            pendingPromotionSquare.isClicked(false);
        }

        wait = false;
        pendingPromotionSquare = null;

        boolean blackKingInCheck = isKingInCheck(false);
        boolean whiteKingInCheck = isKingInCheck(true);

        if (blackKingInCheck || whiteKingInCheck) {
            moveHistory.peek().setCheck();
        }

        this.blackKingSquare.setInCheck(blackKingInCheck);
        this.whiteKingSquare.setInCheck(whiteKingInCheck);

        changeTurns();

        if (!hasAnyLegalMove(isWhiteTurn)) {
            if (isKingInCheck(isWhiteTurn)) {
                System.out.println("Checkmate");
            } else {
                System.out.println("Stalemate");
            }
        }
    }


    /**
     * <b> Main method of the board class. </b>
     * <p>
     * Attempts to move the piece from the start square to the end square.
     * The method ensures the move is legal before making it, and does not leave the
     * king in check. It also updates the king square, turn switching, and checkmate
     * detection.
     * 
     * @param start The square containing the moving piece.
     * @param end   The destination square.
     */
    public void makeMove(GameSquare start, GameSquare end) {
        if (!wait && !start.isEmpty() && start.getPiece().canMove(this, start, end)) {
            boolean playedAudio = false;
            boolean castled = false;
            boolean didEnPassant = false;
            Piece movingPiece = start.getPiece();
            Piece endPiece = end.getPiece();

            if (moveLeavesKingInCheck(movingPiece, start, end)) { // Do not allow the move if it results in self-check
                return;
            }

            // Reset the en Passant targets.
            if (isWhiteTurn) {
                blackEnPassantTarget = null;
            } else {
                whiteEnPassantTarget = null;
            }

            // If the piece is a pawn, perform en Passant updates and check for piece
            // removal.
            if (movingPiece instanceof Pawn) {

                ((Pawn) movingPiece).moved();

                if (Math.abs(start.getRow() - end.getRow()) == 2) {

                    int middleRow = (start.getRow() + end.getRow()) / 2;
                    if (isWhiteTurn) {
                        blackEnPassantTarget = board[middleRow][start.getCol()];
                    } else {
                        whiteEnPassantTarget = board[middleRow][start.getCol()];
                    }
                }

                if (end.isEmpty() && Math.abs(start.getCol() - end.getCol()) == 1) {
                    board[start.getRow()][end.getCol()].removePiece();
                    didEnPassant = true;
                    AudioResources.playAudioOnce(2, playedAudio);
                    playedAudio = true;
                }

                if ((isWhiteTurn && end.getRow() == 0) || (!isWhiteTurn && end.getRow() == 7)) {
                    if (endPiece == null) {
                        AudioResources.playAudioOnce(1, playedAudio);
                    } else if (!playedAudio) {
                        AudioResources.playAudioOnce(2, playedAudio);
                    }

                    end.setPiece(movingPiece);
                    start.removePiece();
                    this.squareClicked(end);

                    wait = true;
                    pendingPromotionSquare = end;
                    mainApp.showPromotionChoices(end, isWhiteTurn);
                    this.moveHistory.push(new Move(start, end, movingPiece, endPiece, true, null, didEnPassant, castled));
                    return;
                }

            } else if (movingPiece instanceof King) { // Make the pieces unable to castle in the future.
                if (Math.abs(start.getCol() - end.getCol()) == 2) {
                    boolean isLeftSide = start.getCol() > end.getCol();
                    int rookCol = isLeftSide ? 0 : 7;
                    int rookEndCol = isLeftSide ? 3 : 5;

                    GameSquare oldRookSquare = this.board[start.getRow()][rookCol];
                    Rook rook = (Rook) oldRookSquare.getPiece();
                    GameSquare newRookSquare = this.board[start.getRow()][rookEndCol];

                    newRookSquare.setPiece(rook);
                    oldRookSquare.removePiece();

                    AudioResources.playAudioOnce(4, playedAudio);
                    playedAudio = true;
                    castled = true;
                }

                updateKingSquare(movingPiece, start, end);

                ((King) movingPiece).setHasMoved();
            } else if (movingPiece instanceof Rook) {
                ((Rook) movingPiece).setHasMoved();
            }

            this.moveHistory.push(new Move(start, end, movingPiece, endPiece, false, null, didEnPassant, castled));


            // Visually and logically update the pieces.
            end.setPiece(movingPiece);
            start.removePiece();

            boolean blackInCheck = isKingInCheck(false);
            boolean whiteInCheck = isKingInCheck(true);

            if (blackInCheck || whiteInCheck) {
                moveHistory.peek().setCheck();
                AudioResources.playAudioOnce(3, playedAudio);
                playedAudio = true;
            }

            if (endPiece == null) {
                AudioResources.playAudioOnce(1, playedAudio);
            } else if (!playedAudio) {
                AudioResources.playAudioOnce(2, playedAudio);
            }

            // Highlight kings if they are in check
            this.blackKingSquare.setInCheck(blackInCheck);
            this.whiteKingSquare.setInCheck(whiteInCheck);


            changeTurns();

            if (!hasAnyLegalMove(isWhiteTurn)) {
                if (isKingInCheck(isWhiteTurn)) {
                    System.out.println("Checkmate");
                } else {
                    System.out.println("Stalemate");
                }
            }
            moveHistory.peek().convertToAlgebraicNotation();
        }
    }

    public void undoMove(){
        if (moveHistory.isEmpty()) return;

    }
}