package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;

public class Knight extends Piece {

    public Knight(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String getName() {
        return "Knight";
    }

    @Override
    public String getColor(){
        return isWhite ? "White" : "Black";
    }

    @Override
    public boolean canMove(ChessBoard board, GameSquare start, GameSquare end) {
        return false;
    }
}
