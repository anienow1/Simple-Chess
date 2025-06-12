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

    public boolean hasMoved() {
        return hasMoved;
    }

    private boolean canCastle(ChessBoard aBoard, GameSquare start, GameSquare end) {
        int startRow = start.getRow();
        int startCol = start.getCol();

        int endCol = end.getCol();

        boolean isLeftSide = startCol > endCol;
        int rookCol = isLeftSide ? 0 : 7;
        GameSquare rookSquare = aBoard.getBoard()[startRow][rookCol];

        if (rookSquare.isEmpty() || !(rookSquare.getPiece() instanceof Rook)) return false;

        Rook rook = (Rook) rookSquare.getPiece();

        if (rook.hasMoved() || !rook.getColor().equals(this.getColor())) return false;

        int direction = isLeftSide ? -1 : 1;


        for (int col = startCol + direction; col != rookCol; col += direction ) {
            GameSquare square = aBoard.getBoard()[startRow][col];
            if (!square.isEmpty() || aBoard.moveLeavesKingInCheck(this, start, end) || aBoard.moveLeavesKingInCheck(this, start, square)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean canMove(ChessBoard aBoard, GameSquare start, GameSquare end) {
        int startRow = start.getRow();
        int startCol = start.getCol();

        int endRow = end.getRow();
        int endCol = end.getCol();

        // Confine movement to 1 square in any direction
        if (Math.abs(startRow - endRow) <= 1 && Math.abs(startCol - endCol) <= 1) {
            if (!end.isEmpty() && end.getPiece().getColor().equals(start.getPiece().getColor())) {
                return false;
            }
            return true;
        }

        if (!hasMoved && startRow == endRow && Math.abs(startCol - endCol) == 2) {
            return this.canCastle(aBoard, start, end);
        }

        return false;
    }
}
