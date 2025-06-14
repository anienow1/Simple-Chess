package Chess;

import Chess.Pieces.Knight;
import Chess.Pieces.Pawn;
import Chess.Pieces.Piece;

public class Move {
    private final GameSquare start;
    private final GameSquare end;
    private final Piece movedPiece;
    private final Piece capturedPiece;
    private final boolean wasPromotion;
    private Piece promotedPiece;
    private boolean wasCheck = false;
    private final boolean wasEnPassant;
    private final boolean wasCastle;

    public Move(GameSquare start, GameSquare end, Piece movedPiece, Piece capturedPiece,
            boolean wasPromotion, Piece promotedPiece,
            boolean wasEnPassant, boolean wasCastle) {
        this.start = start;
        this.end = end;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.wasPromotion = wasPromotion;
        this.promotedPiece = promotedPiece;
        this.wasEnPassant = wasEnPassant;
        this.wasCastle = wasCastle;
    }

    public void convertToAlgebraicNotation() {

        String pieceLetter = movedPiece.getLetter();
        String targetSquare = getSquareString(end.getRow(), end.getCol());
        String capture = "";
        String check = wasCheck ? "+" : "";
        if (capturedPiece != null) {
            capture = "x";
        }
        if (wasCastle) {
            System.out.println(getCastleString());
            return;
        } else if (wasEnPassant) {
            System.out.println(getEnPassantString() + "x" + targetSquare + check);
        } else {
            System.out.println(pieceLetter + capture + targetSquare + check);
        }
    }

    private String getSquareString(int row, int col) {
        char sCol = (char) ('a' + col);
        int sRow = 8 - row;
        return "" + sCol + sRow;
    }

    private String getEnPassantString() {
        char sCol = (char) ('a' + start.getCol());
        return "" + sCol;
    }

    private String getCastleString() {
        if (end.getCol() >= 4) {
            return "0-0";
        } else {
            return "0-0-0";
        }
    }

    public void setPromotedPiece(Piece aPiece) {
        promotedPiece = aPiece;
    }

    public void setCheck() {
        wasCheck = true;
    }

    public boolean wasCastle() {
        return wasCastle;
    }

    public boolean wasEnPassant() {
        return wasEnPassant;
    }

    public boolean wasPromotion() {
        return wasPromotion;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public GameSquare getStart() {
        return start;
    }

    public GameSquare getEnd() {
        return end;
    }

}
