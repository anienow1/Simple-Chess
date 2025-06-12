package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;

public class Rook extends Piece {

    private boolean hasMoved = false;

    public Rook(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public String getName() {
        return "Rook";
    }

    public void setHasMoved() {
        hasMoved = true;
    } 

    public boolean hasMoved() {
        return hasMoved;
    }

    @Override
    public boolean canMove(ChessBoard aBoard, GameSquare start, GameSquare end) {
        if (start.getRow() != end.getRow() && start.getCol() != end.getCol()) return false;

        return isClearPath(aBoard, start, end);
    }
}