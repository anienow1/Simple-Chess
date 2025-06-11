package Chess;

import Chess.Pieces.Piece;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Represents a single square on the chessboard.
 * <p>
 * Inherits from StackPane so it can contain a background color, a 
 * piece image, and other visual indicators like valid move circle highlights.
 */
public class GameSquare extends StackPane {

    // The board that the square belongs to.
    private final ChessBoard board;

    private final int row;
    private final int col;

    private Piece piece;
    private boolean isEmpty = false; // false = has a Piece
    private SquareColor pieceColor; 

    private boolean isSelected = false; // True if currently selected.
    private boolean inCheck = false; // Only applicable to squares with Kings.

    // Visual background color.
    private Rectangle square;

    /*
     * Enum to hold the current state of the square's piece color.
     */
    public enum SquareColor {
        WHITE,
        NONE,
        BLACK
    }

    /**
     * Constructor for a GameSquare object.
     * 
     * @param aRow   The row index on the ChessBoard.
     * @param aCol   The column index on the ChessBoard.
     * @param aPiece The piece starting on the GameSquare (null if empty).
     * @param board  The ChessBoard reference.
     * @param color  The default background color of the GameSquare.
     */
    public GameSquare(int aRow, int aCol, Piece aPiece, ChessBoard board, Color color) {
        square = new Rectangle(board.getSquareSize(), board.getSquareSize(), color);
        this.row = aRow;
        this.col = aCol;
        this.piece = aPiece;
        this.board = board;

        // Set the visibility of the piece on the square.
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

    /**
     * @return The piece currently on this object (returns null if none).
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * @return The enum value representing the color of this square's piece.
     */
    public SquareColor getPieceColor() {
        return this.pieceColor;
    }

    /**
     * @return True if there is no piece on this Square.
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * @return True if this piece is currently selected.
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * @return The natural color of this square, based on position.
     */
    private Color getColor() {
        return (this.row + this.col) % 2 == 0 ? Color.rgb(238, 237, 210) : Color.rgb(117, 148, 91);
    }

    /**
     * Highlights the square red if in check. If not, restore the default color.
     * @param check If true, this square will be set in check.
     */
    public void setInCheck(boolean check) {
        if (this.piece.getName().equals("King")) {
            this.inCheck = check;
            updateColor();
        }
    }

    /**
     * Places a new piece on this square, updating all visual and logical states.
     * @param newPiece The piece to place on this square.
     */
    public void setPiece(Piece newPiece) {

        this.getChildren().removeIf(node -> node instanceof ImageView);

        this.piece = newPiece;
        this.piece.updatePosition(this.row, this.col);
        this.pieceColor = newPiece.getColor() == "White" ? SquareColor.WHITE : SquareColor.BLACK;
        this.isEmpty = false;

        this.getChildren().add(this.piece.getImage());
    }

    /**
     * Removes the current piece from this Square, logically and visually.
     */
    public void removePiece() {
        this.getChildren().removeIf(node -> node instanceof ImageView);
        this.piece = null;
        this.isEmpty = true;

        this.pieceColor = SquareColor.NONE;
    }

    /**
     * Updates the Square's fill color based on it's state. 
     * Red if in check, default color otherwise.
     */
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

    /**
     * Called when any square is clicked.
     * <p>
     * If <I> clicked </I> is true, and this Square contains a piece matching the player's turn color, 
     * highlight the square. If not, reset highlights.
     * 
     * @param clicked Boolean to indicate if the sqaure was or wasn't clicked.
     */
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

    /**
     * Highlights this Square to mark it as a valid move location.
     * <p>
     * If this is an empty square, add a small filled circle. 
     * If this has a piece, add a ring behind the piece.
     * </p>
     */
    public void placeCircle() {
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