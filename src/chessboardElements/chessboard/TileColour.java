package chessboardElements.chessboard;

import java.awt.*;

public enum TileColour {
    WHITE(Color.WHITE),
    BLACK(Color.DARK_GRAY);

    private final Color color;
    // Konstruktor enum dla symbolu
    TileColour(Color color) {
        this.color = color;
    }
    // Getter zwracający symbol figury
    public Color getColour() {
        return color;
    }
}
