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

    private GameSquare[][] board = new GameSquare[8][8];
    private final double SQUARE_SIZE;
    private boolean isWhiteTurn = true;
    public GameSquare selectedSquare;

    private GameSquare whiteKingSquare;
    private GameSquare blackKingSquare;

    public ChessBoard(double windowHeight) {
        SQUARE_SIZE = windowHeight / 8;
        initializeNewBoard();
    }

    private void initializeNewBoard() { // Fill the board with Piece objects
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                Color color;
                if ((row + col) % 2 == 0) {
                    color = Color.rgb(238, 237, 210);
                } else {
                    color = Color.rgb(117, 148, 91);
                }

                if (row == 1 || row == 6) {
                    board[row][col] = new GameSquare(row, col, new Pawn(row == 1 ? false : true, row, col), this,
                            color);
                } else if (row != 0 && row != 7) {
                    board[row][col] = new GameSquare(row, col, null, this, color);
                } else if (col == 0 || col == 7) {
                    board[row][col] = new GameSquare(row, col, new Rook(row == 0 ? false : true, row, col), this,
                            color);
                } else if (col == 1 || col == 6) {
                    board[row][col] = new GameSquare(row, col, new Knight(row == 0 ? false : true, row, col), this,
                            color);
                } else if (col == 2 || col == 5) {
                    board[row][col] = new GameSquare(row, col, new Bishop(row == 0 ? false : true, row, col), this,
                            color);
                } else if (col == 3) {
                    board[row][col] = new GameSquare(row, col, new Queen(row == 0 ? false : true, row, col), this,
                            color);
                } else {
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

    public GameSquare[][] getBoard() {
        return board;
    }

    public double getSquareSize() {
        return SQUARE_SIZE;
    }

    public GameSquare getSelectedSquare() {
        return selectedSquare;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public void changeTurns() {
        isWhiteTurn = !isWhiteTurn;
    }

    public void setSelectedSquare(GameSquare square) {
        selectedSquare = square;
    }

    public boolean colorMatches(GameSquare.SquareColor squareColor, boolean isWhite) {
        if (isWhite && squareColor == GameSquare.SquareColor.WHITE ||
                !isWhite && squareColor == GameSquare.SquareColor.BLACK) {
            return true;
        }
        return false;
    }

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
     * Simulates moving a piece from start to end, checks if the player's king
     * would be in check after the move, then reverts the move.
     * 
     * @param aPiece The piece to move.
     * @param start  The starting square.
     * @param end    The target square.
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
     * game board.
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

    private void markValidSquare(ArrayList<GameSquare> possibleMoves) {
        for (GameSquare square : possibleMoves) {
            square.highlight();
        }
    }

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