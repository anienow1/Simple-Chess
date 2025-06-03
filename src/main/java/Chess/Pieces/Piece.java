package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;

public abstract class Piece {
    
    protected boolean isWhite; // White = True, Black = False

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public abstract String getName();

    public abstract String getColor();

    abstract boolean canMove(ChessBoard board, GameSquare start, GameSquare end);
}
