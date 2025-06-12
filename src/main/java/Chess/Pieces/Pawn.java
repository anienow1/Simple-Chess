package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import Chess.GameSquare.SquareColor;

public class Pawn extends Piece {

    private int moveCount = 0;

    public Pawn(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public String getName() {
        return "Pawn";
    }

    public void moved() {
        moveCount++;
    }

    public int numMoved() {
        return moveCount;
    }

    @Override
    public boolean canMove(ChessBoard aboard, GameSquare start, GameSquare end) {
        if (start.equals(end)) return false;
        
        GameSquare[][] board = aboard.getBoard();

        int startRow = start.getRow();
        int startCol = start.getCol();
        int endRow = end.getRow();
        int endCol = end.getCol();

        int rowDifference = endRow - startRow; // Moving down is positive
        int direction = isWhite ? -1 : 1;
        int colDifference = startCol - endCol;

        boolean movingForward = startCol == endCol;
        boolean oneStep = rowDifference == direction;
        boolean twoSteps = rowDifference == 2 * direction;
        boolean isDiagonal = Math.abs(colDifference) == 1 && rowDifference == direction;

        // Check forward by one.
        if (movingForward && oneStep && end.isEmpty()) {
            return true;
        }

        // Check forward by two if the Pawn has not yet moved.
        if (movingForward && twoSteps && moveCount == 0) {
            if (board[startRow + direction][startCol].isEmpty() && end.isEmpty()) {
                return true;
            }
        }

        // Check diagonal capture
        if (isDiagonal && !end.isEmpty()) {
            if ((isWhite && end.getPieceColor() == SquareColor.BLACK) ||
                    (!isWhite && end.getPieceColor() == SquareColor.WHITE)) {
                return true;
            }
        }

        // En passant
        GameSquare enPassantSquare = aboard.getEnPassantSquare(start.getPieceColor() == GameSquare.SquareColor.WHITE);

        if (isDiagonal && end.isEmpty()) {
            GameSquare adjacentSquare = board[startRow + direction][endCol];

            if (enPassantSquare != null && enPassantSquare.equals(adjacentSquare)) {
                return true;
            }
        }

        return false;
    }
}