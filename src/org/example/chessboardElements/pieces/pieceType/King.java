package org.example.chessboardElements.pieces.pieceType;

import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.pieces.CartesianMovement;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.ChessPieceColors;
import org.example.chessboardElements.pieces.ChessPieceVisuals;

public class King extends ChessPiece implements CartesianMovement {
    public King(ChessPieceColors chessPieceColors, Chessboard chessboard) {
        super(chessPieceColors, chessboard);
        setVisualRepresentation(ChessPieceVisuals.KING);
    }
    @Override
    public void lookAround() {
        getPossibleMoves(true, true,false, false, true, chessboard,color,importantTiles);
    }
}
