package org.example.chessboardElements.pieces.pieceType;

import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.pieces.CartesianMovement;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.ChessPieceColors;
import org.example.chessboardElements.pieces.ChessPieceVisuals;

public class Knight extends ChessPiece implements CartesianMovement {

    public Knight(ChessPieceColors chessPieceColors, Chessboard chessboard) {
        super(chessPieceColors, chessboard);
        setVisualRepresentation(ChessPieceVisuals.KNIGHT);
    }

    public void lookAround(){

    }
}
