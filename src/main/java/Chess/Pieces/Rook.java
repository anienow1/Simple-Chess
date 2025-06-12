package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import javafx.scene.image.ImageView;

public class Rook extends Piece {

    private boolean hasMoved = false;

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

    public void setHasMoved() {
        hasMoved = true;
    } 

    public boolean hasMoved() {
        return hasMoved;
    }

    @Override
    public boolean canMove(ChessBoard aBoard, GameSquare start, GameSquare end) {
        GameSquare[][] board = aBoard.getBoard();
        int startRow = start.getRow();
        int startCol = start.getCol();

        int endRow = end.getRow();
        int endCol = end.getCol();

        if (startRow != endRow && startCol != endCol) return false;

        // Determine which way to go. 
        // If end = start for either row or col, that plane will not be traversed
        int rowStep = Integer.compare(endRow, startRow); 
        int colStep = Integer.compare(endCol, startCol);

        // Don't compare start with itself.
        int row = startRow + rowStep;
        int col = startCol + colStep;

        //Traverse the grid by one each time.
        while (row != endRow || col != endCol) {
            if (!board[row][col].isEmpty()) {
                return false;
            }
            row += rowStep;
            col += colStep;
        }

        if (!end.isEmpty() && end.getPiece().getColor().equals(start.getPiece().getColor())) {
            return false;
        }

        return true;
    }
}