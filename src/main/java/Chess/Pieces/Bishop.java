package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;

public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    public String getName() {
        return "Bishop";
    }

    @Override
    public String getColor() {
        return isWhite ? "White" : "Black";
    }

    @Override
    public boolean canMove(ChessBoard board, GameSquare start, GameSquare end) {
        return false;
    }
}
