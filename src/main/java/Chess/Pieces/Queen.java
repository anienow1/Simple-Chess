package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;

public class Queen extends Piece {

    public Queen(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public String getName() {
        return "Queen";
    }

    @Override
    public boolean canMove(ChessBoard aBoard, GameSquare start, GameSquare end) {
        return isValidRookMove(aBoard, start, end) || isValidBishopMove(aBoard, start, end);
    }

    private boolean isValidRookMove(ChessBoard aBoard, GameSquare start, GameSquare end) {
        if (start.getRow() != end.getRow() && start.getCol() != end.getCol())
            return false;

        return isClearPath(aBoard, start, end);
    }

    private boolean isValidBishopMove(ChessBoard aBoard, GameSquare start, GameSquare end) {
        int rowDifference = Math.abs(start.getRow() - end.getRow());
        int colDifference = Math.abs(start.getCol() - end.getCol());

        if (rowDifference != colDifference)
            return false;

        return isClearPath(aBoard, start, end);
    }

}