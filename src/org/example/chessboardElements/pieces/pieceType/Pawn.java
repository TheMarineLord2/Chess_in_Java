package org.example.chessboardElements.pieces.pieceType;

import org.example.chessboardElements.AvaiableColors;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.pieces.ChessPieceVisuals;

public class Pawn extends ChessPiece {

    public Pawn(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceVisuals.PAWN);
    }

}
