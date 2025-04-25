package org.example.chessboardElements.pieces.pieceType;

import org.example.chessboardElements.ChessPieceColors;
import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.pieces.ChessPieceVisuals;

public class Pawn extends ChessPiece {
    public Pawn(ChessPieceColors chessPieceColors, Chessboard chessboard) {
        super(chessPieceColors, chessboard);
        setVisualRepresentation(ChessPieceVisuals.PAWN);
    }
    
    @Override
    public void lookAround() {
        int direction;
        if(color.equals(ChessPieceColors.WHITE)){
            direction = 1;
        } else { direction = -1; }
        

    }
}
