package chessboardElements.pieces.pieceType;

import chessboardElements.pieces.ChessPiece;
import chessboardElements.chessboard.AvaiableColors;
import chessboardElements.pieces.ChessPieceType;

public class Bishop extends ChessPiece {

    public Bishop(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceType.BISHOP);
    }
}
