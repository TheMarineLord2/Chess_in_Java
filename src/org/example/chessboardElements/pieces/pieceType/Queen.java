package org.example.chessboardElements.pieces.pieceType;

import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.pieces.CartesianMovement;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.ChessPieceColors;
import org.example.chessboardElements.pieces.ChessPieceVisuals;

import java.util.concurrent.Callable;

public class Queen extends ChessPiece implements CartesianMovement {
    public Queen(ChessPieceColors chessPieceColors, Chessboard chessboard) {
        super(chessPieceColors, chessboard);
        setVisualRepresentation(ChessPieceVisuals.QUEEN);
    }

    public void lookAround(){

    }
}
