package org.example.chessboardElements.pieces;

import org.example.chessboardElements.chessboard.Tile;
import org.example.chessboardElements.AvaiableColors;

import java.awt.image.BufferedImage;
import java.util.List;
import java.awt.Color;

public abstract class ChessPiece {
    protected ChessPieceVisuals visualRepresentation;
    protected AvaiableColors color;
    protected List<Tile> tilesSeen;
    protected List<Tile> tilesBlockedBySomeone;

    // updates tilesSeen and tilesUnavaiable
    // implements move pattern of every piece.
    //abstract void lookAtEveryPossibleSpace();
    //

    protected ChessPiece(AvaiableColors avaiableColors) {
        this.color = avaiableColors;
    }
    // getters and setters
    public char getVisualRepresentation() {
        return visualRepresentation.getSymbol();
    }
    public void setVisualRepresentation(ChessPieceVisuals visualRepresentation) {
        this.visualRepresentation = visualRepresentation;
    }
    public AvaiableColors getColor() {
        return color;
    }
    public BufferedImage getChessPieceVisuals() {
        return visualRepresentation.getImage(this.color);
    }
}