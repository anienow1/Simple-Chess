package Chess;

import Chess.Pieces.Piece;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameSquare extends StackPane {
    private final int row;
    private final int col;

    private Piece piece;
    private boolean isEmpty = false; // true = has a Piece
    private SquareColor pieceColor; // Seperate to allow for blank tiles

    private final ChessBoard board;

    private boolean isSelected = false; // Player clicked on it
    private Rectangle square;

    public enum SquareColor {
        WHITE,
        NONE,
        BLACK
    }

    public GameSquare(int aRow, int aCol, Piece aPiece, ChessBoard board, Color color) {
        square = new Rectangle(board.getSquareSize(), board.getSquareSize(), color);
        this.row = aRow;
        this.col = aCol;
        this.piece = aPiece;
        this.board = board;

        if (aPiece == null) {
            this.getChildren().addAll(square);
            isEmpty = true;
        } else {
            this.getChildren().addAll(square, aPiece.getImage());
        }

        if (isEmpty()) {
            this.pieceColor = SquareColor.NONE;
        } else if (this.piece.getColor() == "White") {
            this.pieceColor = SquareColor.WHITE;
        } else {
            this.pieceColor = SquareColor.BLACK;
        }
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

    public SquareColor getPieceColor() {
        return this.pieceColor;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setPiece(Piece newPiece) {
        this.piece = newPiece;
    }

    public boolean isSelected() {
        return isSelected;
    }

    private void setColorAfterClicked() {
        if ((this.row + this.col) % 2 == 0) {
            this.square.setFill(Color.rgb(172, 134, 60));
        } else {
            this.square.setFill(Color.rgb(119, 94, 32));
        }
    }

    public void isClicked(boolean clicked) {
        if (clicked && (board.isWhiteTurn() && this.getPieceColor() == SquareColor.WHITE) ||
                (!board.isWhiteTurn() && this.getPieceColor() == SquareColor.BLACK)) {
            setColorAfterClicked();
        } else {
            if ((this.row + this.col) % 2 == 0) {
                this.square.setFill(Color.rgb(238, 237, 210));
            } else {
                this.square.setFill(Color.rgb(117, 148, 91));
            }
        }
    }

    public void highlight() {
        setColorAfterClicked();
    }
}
