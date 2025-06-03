package com.example.Pieces;

import com.example.ChessBoard;
import com.example.GameSquare;

public class Pawn extends Piece{
    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    //TODO
    @Override
    public boolean canMove(ChessBoard board, GameSquare start, GameSquare end) {
        return false; 
    }
}
