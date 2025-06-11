package Chess.Pieces;

import Chess.ChessBoard;
import Chess.GameSquare;
import javafx.scene.image.ImageView;

public abstract class Piece {

    protected boolean isWhite; // White = True, Black = False
    protected boolean isVisible = true;
    protected int row;
    protected int col;

    public Piece(boolean isWhite, int row, int col) {
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
    }

    public abstract String getName();

    public abstract String getColor();

    public abstract ImageView getImage();

    public boolean getVisibility() {
        return this.isVisible;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public void setVisibility(boolean visible) {
        this.isVisible = visible;
    }

    public void updatePosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public abstract boolean canMove(ChessBoard board, GameSquare start, GameSquare end);
}
