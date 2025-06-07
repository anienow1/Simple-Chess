package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import javafx.scene.image.ImageView;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    public String getName() {
        return "Bishop";
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

        // Check if the row and column align on diagonals
        if (Math.abs(startRow - endRow) != Math.abs(startCol - endCol)) return false;

        // Determine which way to travel
        int rowStep = Integer.compare(endRow, startRow);
        int colStep = Integer.compare(endCol, startCol);

        int row = startRow + rowStep;
        int col = startCol + colStep;

        // Traverse diagonally by one step towards the end square. 
        while (row != endRow && col != endCol) {
            if (!board[row][col].isEmpty()) {
                return false;
            }

            row += rowStep;
            col += colStep;
        }

        if (!end.isEmpty() && 
            end.getPiece().getColor().equals(start.getPiece().getColor())) {
                return false;
            }

        return true;
    }
}
