package com.example;

import com.example.Pieces.Piece;

public class ChessBoard {
    
    private GameSquare[][] board = new GameSquare[8][8];

    public GameSquare[][] getBoard() {
        return board;
    }
}
