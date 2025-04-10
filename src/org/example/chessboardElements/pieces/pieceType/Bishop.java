package org.example.chessboardElements.pieces.pieceType;

import org.example.chessboardElements.AvaiableChessColors;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.pieces.ChessPieceVisuals;

public class Bishop extends ChessPiece {

    public Bishop(AvaiableChessColors avaiableChessColors) {
        super(avaiableChessColors);
        setVisualRepresentation(ChessPieceVisuals.BISHOP);
    }
}
