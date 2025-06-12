package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import Chess.GameSquare.SquareColor;
import javafx.scene.image.ImageView;

public class Pawn extends Piece {

    private int hasMoved = 0;

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

    public void moved() {
        hasMoved++;
    }

    public int numMoved() {
        return hasMoved;
    }

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

        if (start.equals(end))
            return false;

        // Check forward by one.
        if (startCol == endCol && rowDifference == direction && end.isEmpty()) {
            return true;
        }

        // Check forward by two if the Pawn has not yet moved.
        if (startCol == endCol && startRow + direction * 2 == endRow && hasMoved == 0) {
            if (board[startRow + direction][startCol].isEmpty() && end.isEmpty()) {
                return true;
            }
        }

        // Check diagonal capture
        if (rowDifference == direction && colDifference == 1 && !end.isEmpty()) {
            if ((isWhite && end.getPieceColor() == SquareColor.BLACK) ||
                    (!isWhite && end.getPieceColor() == SquareColor.WHITE)) {
                return true;
            }
        }

        // En passant
        GameSquare enPassantSquare = aboard.getEnPassantSquare(start.getPieceColor() == GameSquare.SquareColor.WHITE);

        if (rowDifference == direction && colDifference == 1 && end.isEmpty()) {
            GameSquare adjacentSquare = board[startRow + direction][endCol];

            if (enPassantSquare != null && enPassantSquare.equals(adjacentSquare)) {
                return true;
            }
        }

        return false;
    }
}