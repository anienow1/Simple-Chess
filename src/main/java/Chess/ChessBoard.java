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
                    board[5][4] = new GameSquare(5, 4, new Pawn(false, 5, 4), this, color);
                }
                if (row == 5 && col == 6) {
                    board[5][6] = new GameSquare(5, 6, new Pawn(false, 5, 6), this, color);
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

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public void changeTurns() {
        isWhiteTurn = !isWhiteTurn;
    }

    public void squareClicked(GameSquare square) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                this.getBoard()[row][col].isClicked(false);
            }
        }
        square.isClicked(true);
        if (square.getPiece() != null) {
            ArrayList<GameSquare> squares = this.findPossibleMoves(square.getPiece());
            markValidSquare(squares);
        }
    }

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
        if (possibleMoves.size() == 0) {
            return;
        }
        for (GameSquare square : possibleMoves) {
            square.highlight();
        }
    }
}
