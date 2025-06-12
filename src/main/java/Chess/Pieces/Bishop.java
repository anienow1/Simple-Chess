package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public String getName() {
        return "Bishop";
    }

    @Override
    public boolean canMove(ChessBoard aBoard, GameSquare start, GameSquare end) {
        int rowDifference = Math.abs(start.getRow() - end.getRow());
        int colDifference = Math.abs(start.getCol() - end.getCol());

        if (rowDifference != colDifference) return false;

        return isClearPath(aBoard, start, end);
    }
}
