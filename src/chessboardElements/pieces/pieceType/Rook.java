package chessboardElements.pieces.pieceType;

import chessboardElements.pieces.ChessPiece;
import chessboardElements.chessboard.AvaiableColors;
import chessboardElements.pieces.ChessPieceType;

public class Rook extends ChessPiece {
    public Rook(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceType.ROOK);
    }
}
