package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import javafx.scene.image.ImageView;

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
    public ImageView getImage() {
        return PieceImages.getImage(this);
    }

    @Override
    public boolean canMove(ChessBoard board, GameSquare start, GameSquare end) {
        return false;
    }
}
