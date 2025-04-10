package org.example.chessboardElements.pieces.pieceType;

import org.example.chessboardElements.AvaiableChessColors;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.pieces.ChessPieceVisuals;

public class Pawn extends ChessPiece {

    public Pawn(AvaiableChessColors avaiableChessColors) {
        super(avaiableChessColors);
        setVisualRepresentation(ChessPieceVisuals.PAWN);
    }

}
