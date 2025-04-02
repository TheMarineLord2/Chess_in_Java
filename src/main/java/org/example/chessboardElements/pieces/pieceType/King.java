package chessboardElements.pieces.pieceType;

import chessboardElements.pieces.ChessPiece;
import chessboardElements.AvaiableColors;
import chessboardElements.pieces.ChessPieceVisuals;

public class King extends ChessPiece {
    public King(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceVisuals.KING);
    }
}
