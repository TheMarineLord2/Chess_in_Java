package chessboardElements.pieces.pieceType;

import chessboardElements.pieces.ChessPiece;
import chessboardElements.chessboard.AvaiableColors;
import chessboardElements.pieces.ChessPieceType;

public class Knight extends ChessPiece {

    public Knight(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceType.KNIGHT);
    }
}
