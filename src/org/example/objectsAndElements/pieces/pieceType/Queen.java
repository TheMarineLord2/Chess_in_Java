package org.example.objectsAndElements.pieces.pieceType;

import org.example.objectsAndElements.chessboard.Chessboard;
import org.example.objectsAndElements.pieces.CartesianMovement;
import org.example.objectsAndElements.pieces.ChessPiece;
import org.example.objectsAndElements.ChessPieceColors;
import org.example.objectsAndElements.pieces.ChessPieceVisuals;

public class Queen extends ChessPiece implements CartesianMovement {
    public Queen(ChessPieceColors chessPieceColors, Chessboard chessboard) {
        super(chessPieceColors, chessboard);
        setVisualRepresentation(ChessPieceVisuals.QUEEN);
    }

    public void lookAround(){
        getPossibleMoves(true, true,false, false, false, homeTile, chessboard,color,importantTiles);
    }
}
