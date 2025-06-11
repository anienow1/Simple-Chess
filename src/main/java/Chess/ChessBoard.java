package Chess;

import java.util.ArrayList;

import Chess.Pieces.Bishop;
import Chess.Pieces.King;
import Chess.Pieces.Knight;
import Chess.Pieces.Pawn;
import Chess.Pieces.Piece;
import Chess.Pieces.Queen;
import Chess.Pieces.Rook;
import javafx.scene.paint.Color;

public class ChessBoard {

    // 8 x 8 array representing the board squares
    private GameSquare[][] board = new GameSquare[8][8];
    // Size of each gride pixel, determining by the window height.
    private final double SQUARE_SIZE;
    private boolean isWhiteTurn = true;

    public GameSquare selectedSquare;

    // Refrences to the current tiles holding the two kings.
    private GameSquare whiteKingSquare;
    private GameSquare blackKingSquare;

    // Initialize board and set window height
    public ChessBoard(double windowHeight) {
        SQUARE_SIZE = windowHeight / 8;
        initializeNewBoard();
    }

    /**
     * Set up a full chess board of GameSquare objects.
     * All squares are given colors, and the first and last 2 rows are given
     * pieces in the standard chess layout.
     */
    private void initializeNewBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                // Checkerboard color pattern.
                Color color;
                if ((row + col) % 2 == 0) {
                    color = Color.rgb(238, 237, 210);
                } else {
                    color = Color.rgb(117, 148, 91);
                }

                if (row == 1 || row == 6) { // Pawns
                    board[row][col] = new GameSquare(row, col, new Pawn(row == 1 ? false : true, row, col), this,
                            color);
                } else if (row != 0 && row != 7) { // Empty
                    board[row][col] = new GameSquare(row, col, null, this, color);
                } else if (col == 0 || col == 7) { // Rooks
                    board[row][col] = new GameSquare(row, col, new Rook(row == 0 ? false : true, row, col), this,
                            color);
                } else if (col == 1 || col == 6) { // Knights
                    board[row][col] = new GameSquare(row, col, new Knight(row == 0 ? false : true, row, col), this,
                            color);
                } else if (col == 2 || col == 5) { // Bishop
                    board[row][col] = new GameSquare(row, col, new Bishop(row == 0 ? false : true, row, col), this,
                            color);
                } else if (col == 3) { // Queen
                    board[row][col] = new GameSquare(row, col, new Queen(row == 0 ? false : true, row, col), this,
                            color);
                } else { // Kings. Each is assigned to a kingSquare variable for tracking.
                    board[row][col] = new GameSquare(row, col, new King(row == 0 ? false : true, row, col), this,
                            color);
                    if (row == 0) {
                        blackKingSquare = board[row][col];
                    } else {
                        whiteKingSquare = board[row][col];
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
    private void changeTurns() {
        isWhiteTurn = !isWhiteTurn;
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
        GameSquare kingSquare = kingColorIsWhite ? whiteKingSquare : blackKingSquare; // TODO

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
    private boolean moveLeavesKingInCheck(Piece aPiece, GameSquare start, GameSquare end) {
        Piece capturedPiece = end.getPiece();

        // Make the move
        end.setPiece(aPiece);
        start.removePiece();

        // Save old king square if moving king
        GameSquare oldKingSquare = null;
        if (aPiece.getName().equals("King")) {
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
    public boolean isCheckmate(boolean isWhite) {
        if (!isKingInCheck(isWhite))
            return false;

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
                                return false;
                            }
                        }

                    }
                }
            }
        }
        return true;
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
            square.highlight();
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
     * @param end The destination square.
     */
    public void makeMove(GameSquare start, GameSquare end) {
        if (!start.isEmpty() && start.getPiece().canMove(this, start, end)) {

            Piece movingPiece = start.getPiece();
            if (moveLeavesKingInCheck(movingPiece, start, end)) {
                return;
            }

            end.setPiece(movingPiece);
            start.removePiece();

            if (start.equals(whiteKingSquare)) {
                start.setInCheck(false);
                whiteKingSquare = end;
            } else if (start.equals(blackKingSquare)) {
                start.setInCheck(false);
                blackKingSquare = end;
            }

            // Highlight kings if they are in check
            this.blackKingSquare.setInCheck(isKingInCheck(false));
            this.whiteKingSquare.setInCheck(isKingInCheck(true));

            changeTurns();

            if (isCheckmate(isWhiteTurn))
                System.out.println("Checkmate");
        }
    }
}