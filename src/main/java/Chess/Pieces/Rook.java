package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import javafx.scene.image.ImageView;

public class Rook extends Piece {

    public Rook(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    public String getName() {
        return "Rook";
    }

    @Override
    public String getColor() {
        return isWhite ? "White" : "Black";
    }

    @Override
    public ImageView getImage() {
        return PieceImages.getImage(this);
    }

    @Override
    public boolean canMove(ChessBoard aBoard, GameSquare start, GameSquare end) {
        GameSquare[][] board = aBoard.getBoard();
        int startRow = start.getRow();
        int startCol = start.getCol();

        int endRow = end.getRow();
        int endCol = end.getCol();

        // Check up-down
        if (startRow != endRow && startCol == endCol) { // Rooks cannot move diagonally. 
            for (int row = startRow; row != endRow;) {
                row = (startRow > endRow) ? row - 1 : row + 1;
                if (!board[row][startCol].isEmpty()) { // If a piece is in the way.
                    if (row != endRow || // If the rook is being blocked by the piece, return false.
                            board[row][startCol].getPiece().getColor().equals(start.getPiece().getColor())) {
                        return false;
                    }
                }
            }
            return true; // If the rook made can travel to the end square, return true.
        } 
        //Check left-right.
        else if (startCol != endCol && startRow == endRow) { 
            for (int col = startCol; col != endCol;) {
                col = (startCol > endCol) ? col - 1 : col + 1;
                if (!board[startRow][col].isEmpty()) { // If a piece is in the way.
                    if (col != endCol || // If the rook is being blocked by the piece, return false.
                            board[startRow][col].getPiece().getColor().equals(start.getPiece().getColor())) {
                        return false;
                    }
                }
            }
            return true; // If the rook made can travel to the end square, return true.
        }
        return false;
    }
}