package chessboardElements.pieces.pieceType;

import chessboardElements.pieces.ChessPiece;
import chessboardElements.AvaiableColors;
import chessboardElements.pieces.ChessPieceVisuals;

public class Queen extends ChessPiece {
    public Queen(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceVisuals.QUEEN);
    }
}
