package com.example;

import com.example.Pieces.Piece;

public class GameSquare {
    private final int row;
    private final int col;
    private Piece piece;

    public GameSquare(int aRow, int aCol, Piece aPiece) {
        this.row = aRow;
        this.col = aCol;
        this.piece = aPiece;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.row;
    }

     public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece newPiece) {
        this.piece = newPiece;
    }

   

}
