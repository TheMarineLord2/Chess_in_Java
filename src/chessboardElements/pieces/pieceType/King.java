package chessboardElements.pieces.pieceType;

import chessboardElements.pieces.ChessPiece;
import chessboardElements.chessboard.AvaiableColors;
import chessboardElements.pieces.ChessPieceType;

public class King extends ChessPiece {
    public King(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceType.KING);
    }
}
