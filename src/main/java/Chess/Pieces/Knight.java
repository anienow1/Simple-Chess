package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import javafx.scene.image.ImageView;

public class Knight extends Piece {

    public Knight(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
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
    public ImageView getImage() {
        return PieceImages.getImage(this);
    }

    @Override
    public boolean canMove(ChessBoard aBoard, GameSquare start, GameSquare end) {
        int rowDifference = Math.abs(start.getRow() - end.getRow());
        int colDifference = Math.abs(start.getCol() - end.getCol());

        if ((rowDifference != 2 && colDifference != 2) || (rowDifference != 1 && colDifference != 1)) return false;
        
        return (end.isEmpty() || !end.getPiece().getColor().equals(start.getPiece().getColor()));
    }
}
