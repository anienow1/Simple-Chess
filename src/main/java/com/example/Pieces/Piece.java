package com.example.Pieces;

import com.example.ChessBoard;
import com.example.GameSquare;

public abstract class Piece {
    
    private boolean isWhite; // White = True, Black = False

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    abstract boolean canMove(ChessBoard board, GameSquare start, GameSquare end);
}
