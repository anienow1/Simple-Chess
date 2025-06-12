package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import javafx.scene.image.ImageView;

public abstract class Piece {

    protected boolean isWhite; // White = True, Black = False
    protected int row;
    protected int col;

    public Piece(boolean isWhite, int row, int col) {
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
    }

    public abstract String getName();

    public String getColor() {
        return isWhite ? "White" : "Black";
    }

    public ImageView getImage() {
        return PieceImages.getImage(this);
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void updatePosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public abstract boolean canMove(ChessBoard board, GameSquare start, GameSquare end);

    // Method for Queens, Bishops, and Rooks for straight line logic.
    protected boolean isClearPath(ChessBoard aBoard, GameSquare start, GameSquare end) {
        GameSquare[][] board = aBoard.getBoard();

        int rowStep = Integer.compare(end.getRow(), start.getRow());
        int colStep = Integer.compare(end.getCol(), start.getCol());

        int row = start.getRow() + rowStep;
        int col = start.getCol() + colStep;

        while (row != end.getRow() || col != end.getCol()) {
            if (!board[row][col].isEmpty()) {
                return false;
            }
            row += rowStep;
            col += colStep;
        }

        return end.isEmpty() || !end.getPiece().getColor().equals(this.getColor());
    }
}
