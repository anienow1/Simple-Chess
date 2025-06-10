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
                }

                if (row == 5 && col == 4) {
                    board[5][4] = new GameSquare(5, 4, new Knight(false, 5, 4), this, color);
                }
                if (row == 5 && col == 6) {
                    board[5][6] = new GameSquare(5, 6, new Knight(false, 5, 6), this, color);
                }
                if (row == 5 && col == 3) {
                    board[5][3] = new GameSquare(5, 3, new Knight(true, 5, 3), this, color);
                }
                if (row == 2 && col == 2) {
                    board[2][2] = new GameSquare(2, 2, new Knight(false, 2, 2), this, color);

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

    /**
     * Searches for and returns all valid moves for the input piece to make on the
     * game board.
     * 
     * @param aPiece Chess piece to find the valid moves for.
     * @return An ArrayList of all legal moves for the input piece to make.
     */
    private ArrayList<GameSquare> findPossibleMoves(Piece aPiece) {
        ArrayList<GameSquare> possibleMoves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (aPiece.canMove(this, board[aPiece.getRow()][aPiece.getCol()], board[row][col])) { // Requires start
                                                                                                      // square
                    possibleMoves.add(board[row][col]);
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
        System.out.println(start.getRow());
        if (!start.isEmpty() && start.getPiece().canMove(this, start, end)) {
            end.setPiece(start.getPiece());
            changeTurns();
            start.removePiece();
        }
    }
}
