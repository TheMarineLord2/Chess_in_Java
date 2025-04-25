package org.example.chessboardElements.pieces.pieceType;

import org.example.chessboardElements.ChessPieceColors;
import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.pieces.CartesianMovement;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.pieces.ChessPieceVisuals;

public class Bishop extends ChessPiece implements CartesianMovement {

    public Bishop(ChessPieceColors chessPieceColors, Chessboard chessboard) {
        super(chessPieceColors, chessboard);
        setVisualRepresentation(ChessPieceVisuals.BISHOP);
    }

    public void lookAround(){
    }
}
