package org.example.objectsAndElements.pieces.pieceType;

import org.example.objectsAndElements.ChessPieceColors;
import org.example.objectsAndElements.chessboard.Chessboard;
import org.example.objectsAndElements.pieces.CartesianMovement;
import org.example.objectsAndElements.pieces.ChessPiece;
import org.example.objectsAndElements.pieces.ChessPieceVisuals;

public class Pawn extends ChessPiece implements CartesianMovement {
    public Pawn(ChessPieceColors chessPieceColors, Chessboard chessboard) {
        super(chessPieceColors, chessboard);
        setVisualRepresentation(ChessPieceVisuals.PAWN);
    }
    
    @Override
    public void lookAround() {
        getPossibleMoves(false, false,true, false, false, homeTile, chessboard,color,importantTiles);
    }
}
