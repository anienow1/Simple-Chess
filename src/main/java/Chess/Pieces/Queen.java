package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import javafx.scene.image.ImageView;

public class Queen extends Piece {

    public Queen(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    public String getName() {
        return "Queen";
    }

    @Override
    public String getColor() {
        return isWhite ? "White" : "Black";
    }

    @Override
    public ImageView getImage() {
        return PieceImages.getImage(this);
    }

    @Override
    public boolean canMove(ChessBoard aBoard, GameSquare start, GameSquare end) {
        // Use the move logic for rooks and bishops to determine valid moves.
        return (checkBishopMovement(aBoard, start, end) || checkRookMovement(aBoard, start, end));
    }

    /**
     * Helper method for canMove
     * 
     * @return Move validity for rooks.
     */
    private boolean checkRookMovement(ChessBoard aBoard, GameSquare start, GameSquare end) {
        GameSquare[][] board = aBoard.getBoard();
        int startRow = start.getRow();
        int startCol = start.getCol();

        int endRow = end.getRow();
        int endCol = end.getCol();

        if (startRow != endRow && startCol != endCol)
            return false;

        // Determine which way to go.
        // If end = start for either row or col, that plane will not be traversed
        int rowStep = Integer.compare(endRow, startRow);
        int colStep = Integer.compare(endCol, startCol);

        // Don't compare start with itself.
        int row = startRow + rowStep;
        int col = startCol + colStep;

        // Traverse the grid by one each time.
        while (row != endRow || col != endCol) {
            if (!board[row][col].isEmpty()) {
                return false;
            }
            row += rowStep;
            col += colStep;
        }

        if (!end.isEmpty() && end.getPiece().getColor().equals(start.getPiece().getColor())) {
            return false;
        }

        return true;
    }

    /**
     * Helper method for canMove
     * 
     * @return Move validity for bishops.
     */
    private boolean checkBishopMovement(ChessBoard aBoard, GameSquare start, GameSquare end) {
        GameSquare[][] board = aBoard.getBoard();
        int startRow = start.getRow();
        int startCol = start.getCol();

        int endRow = end.getRow();
        int endCol = end.getCol();

        // If the start and end are not along diagonals, return false;
        if (Math.abs(startRow - endRow) != Math.abs(startCol - endCol))
            return false;

        // Determine which way to travel
        int rowStep = Integer.compare(endRow, startRow);
        int colStep = Integer.compare(endCol, startCol);

        int row = startRow + rowStep;
        int col = startCol + colStep;

        // Traverse diagonal by one step.
        // Stops if it reaches the end square, or if a piece is in the way.
        while (row != endRow && col != endCol) {
            if (!board[row][col].isEmpty()) {
                return false;
            }

            row += rowStep;
            col += colStep;
        }

        if (!end.isEmpty() &&
                end.getPiece().getColor().equals(start.getPiece().getColor())) {
            return false;
        }

        return true;
    }
}