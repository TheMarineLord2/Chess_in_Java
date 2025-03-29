package chessboardElements.pieces;

import chessboardElements.chessboard.Tile;
import chessboardElements.chessboard.AvaiableColors;

import java.util.List;
import java.awt.Color;

public abstract class ChessPiece {
    private ChessPieceType visualRepresentation;
    private Color color;
    private List<Tile> tilesPotentiallyAttacked;

    protected ChessPiece(AvaiableColors avaiableColors) {
        this.color = avaiableColors.getPieceColor();
    }

    public char getVisualRepresentation() {
        return visualRepresentation.getSymbol();
    }

    public void setVisualRepresentation(ChessPieceType visualRepresentation) {
        this.visualRepresentation = visualRepresentation;
    }

    public Color getColor() {
        return color;
    }
}
