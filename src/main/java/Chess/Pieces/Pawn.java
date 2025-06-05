package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import javafx.scene.image.ImageView;

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

    @Override 
    public ImageView getImage() {
        return PieceImages.getImage(this);
    }

    // TODO
    @Override
    public boolean canMove(ChessBoard board, GameSquare start, GameSquare end) {
        return false;
    }
}
