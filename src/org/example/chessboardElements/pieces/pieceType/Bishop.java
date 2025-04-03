package org.example.chessboardElements.pieces.pieceType;

import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.AvaiableColors;
import org.example.chessboardElements.pieces.ChessPieceVisuals;

public class Bishop extends ChessPiece {

    public Bishop(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceVisuals.BISHOP);
    }
}
