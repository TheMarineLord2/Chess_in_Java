package org.example.objectsAndElements.pieces.pieceType;

import org.example.objectsAndElements.ChessPieceColors;
import org.example.objectsAndElements.chessboard.Chessboard;
import org.example.objectsAndElements.pieces.CartesianMovement;
import org.example.objectsAndElements.pieces.ChessPiece;
import org.example.objectsAndElements.pieces.ChessPieceVisuals;

public class Bishop extends ChessPiece implements CartesianMovement {

    public Bishop(ChessPieceColors chessPieceColors, Chessboard chessboard) {
        super(chessPieceColors, chessboard);
        setVisualRepresentation(ChessPieceVisuals.BISHOP);
    }

    public void lookAround(){
        getPossibleMoves(true, false,false, false, false,homeTile, chessboard,color,importantTiles);
    }
}
