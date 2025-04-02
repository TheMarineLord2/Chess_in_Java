package chessboardElements.pieces.pieceType;

import chessboardElements.pieces.ChessPiece;
import chessboardElements.AvaiableColors;
import chessboardElements.pieces.ChessPieceVisuals;

public class Knight extends ChessPiece {

    public Knight(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceVisuals.KNIGHT);
    }
}
