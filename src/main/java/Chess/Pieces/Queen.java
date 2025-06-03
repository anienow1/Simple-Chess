package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;

public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    public String getName() {
        return "Queen";
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
