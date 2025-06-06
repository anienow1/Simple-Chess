package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import Chess.GameSquare.SquareColor;
import javafx.scene.image.ImageView;

public class Pawn extends Piece {

    private boolean hasMoved = false;

    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    public String getName() {
        return "Pawn";
    }

    @Override
    public String getColor() {
        return isWhite ? "White" : "Black";
    }

    @Override
    public ImageView getImage() {
        return PieceImages.getImage(this);
    }

    public void setHasMoved() {
        hasMoved = true;
    }

    // TODO
    @Override
    public boolean canMove(ChessBoard aboard, GameSquare start, GameSquare end) {
        GameSquare[][] board = aboard.getBoard();
        int startRow = start.getRow();
        int startCol = start.getCol();

        int endRow = end.getRow();
        int endCol = end.getCol();

        if (Math.abs(startRow - endRow) > 2 || Math.abs(startCol - endCol) > 1) { // If moving by more than 2x0 or 1x1, return false
            return false;
        } else if (hasMoved && Math.abs(startRow - endRow) == 2) { // If already moved, pawns cannot move forward 2
            return false;
        }

        if (isWhite) {
            if (!hasMoved && (endRow - startRow) == 2) {
                if (board[startRow - 1][startCol].isEmpty() && end.isEmpty()) {
                    return true;
                }
            } else if ((endRow - startRow) == 1) {
                if (endCol == startCol && end.isEmpty()) {
                    return true;
                } else if (Math.abs(startCol - endCol) == 1 && end.getPieceColor() == SquareColor.BLACK) {
                    return true;
                }
            }

        } else {

        }
        return false;
    }
}
