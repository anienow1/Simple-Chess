package Chess;

import Chess.Pieces.Piece;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameSquare extends Rectangle {
    private final int row;
    private final int col;
    private Piece piece;
    private final ChessBoard board;

    public GameSquare(int aRow, int aCol, Piece aPiece, ChessBoard board) {
        super(board.getSquareSize(), board.getSquareSize(), Color.BLACK);
        this.row = aRow;
        this.col = aCol;
        this.piece = aPiece;
        this.board = board;       
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece newPiece) {
        this.piece = newPiece;
    }
}
