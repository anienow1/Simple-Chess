package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;

public class King extends Piece {

    public King(boolean isWhite) {
        super(isWhite);
    }

    public String getName() {
        return "King";
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
