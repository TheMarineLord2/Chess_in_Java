package org.example.chessboardElements;

import java.awt.*;

public enum SpecialTileColors {
    /**
     * Light blue color to represent available tiles for movement.
     */
    AVAILABLE_TILE(new Color(29, 178, 245)),

    /**
     * Green color to represent occupied tiles (friendly pieces).
     */
    HOME_TILE(new Color(117, 255, 26)),

    /**
     * Red color to indicate enemy pieces that can be captured.
     */
    ENEMY_TILE(new Color(189, 0, 24)),

    /**
     * Yellow color to represent special moves (e.g., castling, en passant).
     */
    SPECIAL_MOVE(new Color(255, 213, 0));

    private final Color color;

    SpecialTileColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
