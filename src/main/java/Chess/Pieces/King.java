package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;

public class King extends Piece {

    private boolean hasMoved = false;

    public King(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public String getName() {
        return "King";
    }

    public void setHasMoved() {
        hasMoved = true;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    private boolean canCastle(ChessBoard aBoard, GameSquare start, GameSquare end) {
        int row = start.getRow();
        int startCol = start.getCol();
        int endCol = end.getCol();

        boolean isLeftSide = startCol > endCol;
        int rookCol = isLeftSide ? 0 : 7;
        int direction = isLeftSide ? -1 : 1;

        GameSquare rookSquare = aBoard.getBoard()[row][rookCol];

        // Rook is present and unmoved.
        if (rookSquare.isEmpty() || !(rookSquare.getPiece() instanceof Rook)) return false;

        Rook rook = (Rook) rookSquare.getPiece();
        if (rook.hasMoved()) return false;

        // The squares between the king and rook must be empty and not threatened.
        for (int col = startCol + direction; col != rookCol; col += direction ) {
            GameSquare square = aBoard.getBoard()[row][col];
            if (!square.isEmpty() || aBoard.moveLeavesKingInCheck(this, start, square)) {
                return false;
            }
        }

        return !aBoard.moveLeavesKingInCheck(this, start, end);
    }

    @Override
    public boolean canMove(ChessBoard aBoard, GameSquare start, GameSquare end) {
        int startRow = start.getRow();
        int startCol = start.getCol();
        int endRow = end.getRow();
        int endCol = end.getCol();

        int rowDifference = (Math.abs(startRow - endRow));
        int colDifference = (Math.abs(startCol - endCol));

        // Confine movement to 1 square in any direction
        if (rowDifference <= 1 && colDifference <= 1) {
            return (end.isEmpty() || !end.getPiece().getColor().equals(this.getColor()));
        }

        if (!hasMoved && rowDifference == 0 && colDifference == 2) {
            return this.canCastle(aBoard, start, end);
        }

        return false;
    }
}
