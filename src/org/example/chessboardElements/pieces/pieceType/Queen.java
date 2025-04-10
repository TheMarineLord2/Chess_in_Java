package org.example.chessboardElements.pieces.pieceType;

import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.AvaiableChessColors;
import org.example.chessboardElements.pieces.ChessPieceVisuals;

public class Queen extends ChessPiece {
    public Queen(AvaiableChessColors avaiableChessColors) {
        super(avaiableChessColors);
        setVisualRepresentation(ChessPieceVisuals.QUEEN);
    }
}
