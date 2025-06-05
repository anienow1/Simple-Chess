package Chess.Pieces;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class PieceImages {
    private static final Image black = new Image("Pieces_Black.png");
    private static final Image white = new Image("Pieces_White.png");

    private PieceImages() { // Prevent Object Creation
    }

    public static ImageView getImage(Piece aPiece) {
        boolean isWhite = aPiece.isWhite;
        String pieceType = aPiece.getName();

        ImageView img = isWhite ? new ImageView(white) : new ImageView(black);

        switch (pieceType) {
            case ("King"):
                img.setViewport(new Rectangle2D(2, 0, 107, 110));
                break;
            case ("Queen"):
                img.setViewport(new Rectangle2D(126, 0, 125, 110));
                break;
            case ("Bishop"):
                img.setViewport(new Rectangle2D(270, 0, 100, 105));
                break;
            case ("Rook"):
                img.setViewport(new Rectangle2D(545, 0, 85, 105));
                break;
            case ("Knight"):
                img.setViewport(new Rectangle2D(400, 0, 103, 105));
                break;
            case ("Pawn"):
                img.setViewport(new Rectangle2D(680, 0, 76, 115));
                break;
            default:
                System.out.println(pieceType + " is not a valid type.");
        }

        return img;

    }
}