package chessboardElements.pieces.pieceType;

import chessboardElements.pieces.ChessPiece;
import chessboardElements.AvaiableColors;
import chessboardElements.pieces.ChessPieceVisuals;

public class Bishop extends ChessPiece {

    public Bishop(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceVisuals.BISHOP);
    }
}
