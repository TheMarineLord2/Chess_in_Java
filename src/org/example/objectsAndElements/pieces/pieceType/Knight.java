package org.example.objectsAndElements.pieces.pieceType;

import org.example.objectsAndElements.chessboard.Chessboard;
import org.example.objectsAndElements.pieces.CartesianMovement;
import org.example.objectsAndElements.pieces.ChessPiece;
import org.example.objectsAndElements.ChessPieceColors;
import org.example.objectsAndElements.pieces.ChessPieceVisuals;

public class Knight extends ChessPiece implements CartesianMovement {

    public Knight(ChessPieceColors chessPieceColors, Chessboard chessboard) {
        super(chessPieceColors, chessboard);
        setVisualRepresentation(ChessPieceVisuals.KNIGHT);
    }

    public void lookAround(){
        getPossibleMoves(false, false,false, true, false, homeTile, chessboard,color,importantTiles);
    }
}
