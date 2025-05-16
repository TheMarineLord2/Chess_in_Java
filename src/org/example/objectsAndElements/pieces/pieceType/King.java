package org.example.objectsAndElements.pieces.pieceType;

import org.example.objectsAndElements.chessboard.Chessboard;
import org.example.objectsAndElements.pieces.CartesianMovement;
import org.example.objectsAndElements.pieces.ChessPiece;
import org.example.objectsAndElements.ChessPieceColors;
import org.example.objectsAndElements.pieces.ChessPieceVisuals;

public class King extends ChessPiece implements CartesianMovement {
    public King(ChessPieceColors chessPieceColors, Chessboard chessboard) {
        super(chessPieceColors, chessboard);
        setVisualRepresentation(ChessPieceVisuals.KING);
    }
    @Override
    public void lookAround() {
        getPossibleMoves(false, false,false, false, true, homeTile, chessboard, color, importantTiles);
    }
}
