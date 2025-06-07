package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import Chess.GameSquare.SquareColor;
import javafx.scene.image.ImageView;

public class Pawn extends Piece {

    private boolean hasMoved = false;

    public Pawn(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
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

        int rowDifference = endRow - startRow; // Moving down is positive
        int direction = isWhite ? -1 : 1; 
        int colDifference = Math.abs(startCol - endCol);

        if (start.equals(end)) return false;

        // Check forward by one.
        if (startCol == endCol && rowDifference == direction && end.isEmpty()) { 
            return true;
        }

        // Check forward by two if the Pawn has not yet moved.
        if (startCol == endCol && startRow + direction * 2 == endRow && !hasMoved) { 
            if (board[startRow + direction][startCol].isEmpty() && end.isEmpty()) {
                return true;
            }
        }

        if (rowDifference == direction && colDifference == 1 && !end.isEmpty()) {
            if ((isWhite && end.getPieceColor() == SquareColor.BLACK) ||
                (!isWhite && end.getPieceColor() == SquareColor.WHITE)) {
                    return true;
                }
        }
        return false;
    }
}