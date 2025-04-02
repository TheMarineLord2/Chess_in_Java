package chessboardElements.pieces.pieceType;

import chessboardElements.AvaiableColors;
import chessboardElements.pieces.ChessPiece;
import chessboardElements.pieces.ChessPieceVisuals;

public class Pawn extends ChessPiece {

    public Pawn(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceVisuals.PAWN);
    }

}
