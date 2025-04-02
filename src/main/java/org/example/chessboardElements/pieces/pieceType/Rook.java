package chessboardElements.pieces.pieceType;

import chessboardElements.pieces.ChessPiece;
import chessboardElements.AvaiableColors;
import chessboardElements.pieces.ChessPieceVisuals;

public class Rook extends ChessPiece {
    public Rook(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceVisuals.ROOK);
    }
}
