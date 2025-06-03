package com.example.Pieces;

import com.example.ChessBoard;
import com.example.GameSquare;

public class King extends Piece{

    public King(boolean isWhite) {
        super(isWhite);
    }
    
    @Override
    public boolean canMove(ChessBoard board, GameSquare start, GameSquare end) {
        return false; 
    }
}
