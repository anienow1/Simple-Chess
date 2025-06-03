package com.example.Pieces;

import com.example.ChessBoard;
import com.example.GameSquare;

public class Queen extends Piece{

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean canMove(ChessBoard board, GameSquare start, GameSquare end) {
        return false; 
    }
}
