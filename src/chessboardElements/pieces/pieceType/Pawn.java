package chessboardElements.pieces.pieceType;

import chessboardElements.chessboard.AvaiableColors;
import chessboardElements.pieces.ChessPiece;
import chessboardElements.pieces.ChessPieceType;

public class Pawn extends ChessPiece {

    public Pawn(AvaiableColors avaiableColors) {
        super(avaiableColors);
        setVisualRepresentation(ChessPieceType.PAWN);
    }

}
