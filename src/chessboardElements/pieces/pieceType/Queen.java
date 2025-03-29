package chessboardElements.pieces.pieceType;

import chessboardElements.pieces.ChessPiece;
import chessboardElements.chessboard.AvaiableColors;
import chessboardElements.pieces.ChessPieceType;

public class Queen extends ChessPiece {
    public Queen(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceType.QUEEN);
    }
}
