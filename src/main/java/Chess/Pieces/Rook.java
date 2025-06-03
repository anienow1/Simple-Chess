package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        super(isWhite);
    }

    public String getName() {
        return "Rook";
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
