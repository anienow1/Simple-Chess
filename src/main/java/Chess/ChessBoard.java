package Chess;

import Chess.Pieces.Bishop;
import Chess.Pieces.King;
import Chess.Pieces.Knight;
import Chess.Pieces.Pawn;
import Chess.Pieces.Piece;
import Chess.Pieces.Queen;
import Chess.Pieces.Rook;

public class ChessBoard {

    private GameSquare[][] board = new GameSquare[8][8];
    private final double SQUARE_SIZE;

    public ChessBoard(double windowHeight) {
        SQUARE_SIZE = windowHeight / 8;
        initializeNewBoard();
    }

    private void initializeNewBoard() { // Fill the board with Piece objects
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row == 1 || row == 6) {
                    board[row][col] = new GameSquare(row, col, new Pawn(row == 1 ? false : true), this);
                } else if (row != 0 && row != 7) {
                    board[row][col] = new GameSquare(row, col, null, this);
                } else if (col == 0 || col == 7) {
                    board[row][col] = new GameSquare(row, col, new Rook(row == 0 ? false : true), this);
                } else if (col == 1 || col == 6) {
                    board[row][col] = new GameSquare(row, col, new Knight(row == 0 ? false : true), this);
                } else if (col == 2 || col == 5) {
                    board[row][col] = new GameSquare(row, col, new Bishop(row == 0 ? false : true), this);
                } else if (col == 3) {
                    board[row][col] = new GameSquare(row, col, new Queen(row == 0 ? false : true), this);
                } else {
                    board[row][col] = new GameSquare(row, col, new King(row == 0 ? false : true), this);
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
}
