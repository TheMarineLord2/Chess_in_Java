package org.example.objectsAndElements.pieces.pieceType;

import org.example.objectsAndElements.chessboard.Chessboard;
import org.example.objectsAndElements.pieces.CartesianMovement;
import org.example.objectsAndElements.pieces.ChessPiece;
import org.example.objectsAndElements.ChessPieceColors;
import org.example.objectsAndElements.pieces.ChessPieceVisuals;

public class Rook extends ChessPiece implements CartesianMovement {
    public Rook(ChessPieceColors chessPieceColors, Chessboard chessboard) {
        super(chessPieceColors, chessboard);
        setVisualRepresentation(ChessPieceVisuals.ROOK);
    }

    public void lookAround(){
        getPossibleMoves(false, true,false, false, false, homeTile, chessboard,color,importantTiles);
    }
}
