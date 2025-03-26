package chessboardElements.chessboard;

import java.awt.*;

public enum TileColor {
    WHITE(Color.LIGHT_GRAY),
    BLACK(Color.DARK_GRAY);

    private final Color color;
    // Konstruktor pozala na zapisanie danych w enum.
    TileColor(Color color) {
        this.color = color;
    }
    // Zwroc przypisany kolor:
    public Color getColor() {
        return color;
    }
}
