package chessboardElements.chessboard;

import java.awt.*;

public enum AvaiableColors {
    WHITE(Color.LIGHT_GRAY, Color.WHITE),
    BLACK(Color.DARK_GRAY, Color.BLACK);

    private final Color tileColor;
    private final Color pieceColor;

    // Konstruktor pozala na zapisanie danych w enum.
    AvaiableColors(Color tileColor, Color pieceColor) {
        this.tileColor = tileColor;
        this.pieceColor = pieceColor;
    }

    // Zwroc przypisany kolor kafelka:
    public Color getTileColor() {
        return tileColor;
    }
    // Zwroc przypisany kolor figury:
    public Color getPieceColor() {
        return pieceColor;
    }


}
