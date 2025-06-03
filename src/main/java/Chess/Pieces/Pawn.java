package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;

public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    public String getName() {
        return "Pawn";
    }

    @Override
    public String getColor(){
        return isWhite ? "White" : "Black";
    }

    // TODO
    @Override
    public boolean canMove(ChessBoard board, GameSquare start, GameSquare end) {
        return false;
    }
}
