package org.example.chessboardElements.pieces.pieceType;

import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.pieces.CartesianMovement;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.ChessPieceColors;
import org.example.chessboardElements.pieces.ChessPieceVisuals;

public class Rook extends ChessPiece implements CartesianMovement {
    public Rook(ChessPieceColors chessPieceColors, Chessboard chessboard) {
        super(chessPieceColors, chessboard);
        setVisualRepresentation(ChessPieceVisuals.ROOK);
    }

    public void lookAround(){

    }
}
