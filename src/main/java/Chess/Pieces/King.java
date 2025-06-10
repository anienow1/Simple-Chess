package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import javafx.scene.image.ImageView;

public class King extends Piece {

    private boolean hasMoved = false;

    public King(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    public String getName() {
        return "King";
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

    @Override
    public boolean canMove(ChessBoard aBoard, GameSquare start, GameSquare end) {
        int startRow = start.getRow();
        int startCol = start.getCol();

        int endRow = end.getRow();
        int endCol = end.getCol();

        // Confine movement to 1 square in any direction
        if (Math.abs(startRow - endRow) > 1 || Math.abs(startCol - endCol) > 1)
            return false;

        // Cannot capture same color piece
        if (!end.isEmpty() && 
            end.getPiece().getColor().equals(start.getPiece().getColor())) {
                return false;
            }

            return true;
    }
}
