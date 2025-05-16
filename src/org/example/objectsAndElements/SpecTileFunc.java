package org.example.objectsAndElements;

import java.awt.*;

public enum SpecTileFunc {
    /**
     * Light blue color to represent available tiles for movement.
     */
    AVAILABLE_TILE(new Color(29, 178, 245)),

    /**
     * Red color to indicate enemy pieces that can be captured.
     */
    ENEMY_TILE(new Color(189, 0, 24)),

    /**
     * Yellow color to represent special moves (e.g., castling, en passant).
     */
    SPECIAL_MOVE(new Color(255, 213, 0)),
    /** abstract tile color usefull when looking for potential pins,
     * checks and everything threatening important tiles */
    POTENTIALLY_OBSERVED(new Color(255, 0, 200, 221));

    private final Color color;

    SpecTileFunc(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
