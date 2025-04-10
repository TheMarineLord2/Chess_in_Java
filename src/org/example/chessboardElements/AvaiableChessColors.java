package org.example.chessboardElements;

import java.awt.*;
//import java.security.PublicKey;

public enum AvaiableChessColors {
    WHITE(Color.getHSBColor(69,0.05f,0.95f), Color.getHSBColor(0,0.0f,0.75f),
            Color.getHSBColor(0,0.0f,1.0f)),
    BLACK(Color.getHSBColor(69,0.05f,0.2f), Color.getHSBColor(0,0.0f,0.25f),
            Color.getHSBColor(0,0.0f,0.0f));

    private final Color tileColor;
    private final Color pieceColor;
    private final Color shadowColor;

    // Konstruktor pozala na zapisanie danych w enum.
    AvaiableChessColors(Color tileColor, Color pieceColor, Color shadowColor) {
        this.tileColor = tileColor;
        this.pieceColor = pieceColor;
        this.shadowColor = shadowColor;
    }

    // Zwroc przypisany kolor kafelka:
    public Color getTileColor() {
        return tileColor;
    }
    // Zwroc przypisany kolor figury:
    public Color getPieceColor() {
        return pieceColor;
    }
    public Color getShadowColor() {
        return shadowColor;
    }

}
