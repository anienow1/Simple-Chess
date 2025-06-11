package Chess;

import Chess.Pieces.Piece;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameSquare extends StackPane {
    private final int row;
    private final int col;

    private Piece piece;
    private boolean isEmpty = false; // true = has a Piece
    private SquareColor pieceColor; // Seperate to allow for blank tiles

    private final ChessBoard board;

    private boolean isSelected = false; // Player clicked on it
    private boolean inCheck = false; // Only applicable to squares with Kings.
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

    public void setInCheck(boolean check) {
        this.inCheck = check;
        updateColor();
    }

    public void setPiece(Piece newPiece) {

        this.getChildren().removeIf(node -> node instanceof ImageView);

        this.piece = newPiece;
        this.piece.updatePosition(this.row, this.col);
        this.pieceColor = newPiece.getColor() == "White" ? SquareColor.WHITE : SquareColor.BLACK;
        this.isEmpty = false;

        this.getChildren().add(this.piece.getImage());
    }

    public void removePiece() {
        this.getChildren().removeIf(node -> node instanceof ImageView);
        this.piece = null;
        this.isEmpty = true;

        this.pieceColor = SquareColor.NONE;
    }

    public boolean isSelected() {
        return isSelected;
    }

    private Color getColor() {
        return (this.row + this.col) % 2 == 0 ? Color.rgb(238, 237, 210) : Color.rgb(117, 148, 91);
    }

    private void updateColor() {
        if (inCheck) {
            if ((this.row + this.col) % 2 == 0) {
                this.square.setFill(Color.rgb(243, 62, 66));
            } else {
                this.square.setFill(Color.rgb(231, 53, 54));
            }
        } else {
            this.square.setFill(getColor());
        }
    }

    public void isClicked(boolean clicked) { // All square pieces perform this method after the board is clicked.
        if (inCheck)
            return;

        if (clicked && ((board.isWhiteTurn() && this.getPieceColor() == SquareColor.WHITE) || // If the click is on a
                                                                                              // piece, and it is that
                                                                                              // piece color's turn,
                (!board.isWhiteTurn() && this.getPieceColor() == SquareColor.BLACK))) { // then highlight it.
            Color fillColor = (this.row + this.col % 2 == 0) ? (Color.rgb(172, 134, 60)) : (Color.rgb(119, 94, 32));
            this.square.setFill(fillColor);

        } else { // If the square wasn't clicked, set it to default color and remove circles from
                 // it.
            if (!inCheck) {
                updateColor();
            }
            this.getChildren().removeIf(node -> node instanceof Circle);
        }
    }

    public void highlight() {
        Circle circle = new Circle();
        circle.setFill(this.getColor().darker());

        if (this.isEmpty) {
            circle.setRadius(25);
            this.getChildren().add(circle);
        } else {
            circle.setFill(null);
            circle.setStroke(this.getColor().darker());
            circle.setStrokeWidth(8);
            circle.setRadius(62);

            this.getChildren().removeIf(node -> node instanceof ImageView);
            this.getChildren().addAll(circle, this.piece.getImage());
        }
    }
}