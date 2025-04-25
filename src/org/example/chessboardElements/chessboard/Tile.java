package org.example.chessboardElements.chessboard;

import org.example.chessboardElements.ChessPieceColors;
import org.example.chessboardElements.SpecTileFunc;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.pieces.pieceType.King;
import org.example.chessboardElements.pieces.pieceType.Rook;
import org.example.mainControllers.gameControlls.GameOperator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


/**
 * The Tile class represents a single square or field on the chessboard.
 * It holds information about its color, position, contained chess piece, and visual representation.
 * It also provides methods for handling interactions and rendering.
 */
public class Tile {
    // The JButton visual component representing the tile on the chessboard.
    private JButton button;
    // The original color of the tile, either BLACK or WHITE.
    private final ChessPieceColors tileColor;
    // The chess piece currently occupying this tile, or null if empty.
    private ChessPiece chessPiece = null;
    // The row and column coordinates of the tile on the chessboard.
    private int row, column;
    // A list of chess pieces observing this tile (used for special rules like attacks).
    private List<ChessPiece> listOfObservers = new java.util.ArrayList<>();
    // A MouseAdapter to handle mouse interactions on the tile.
    private Tile selfRefference;
    private MouseAdapter interactiveAdapter;
    private MouseAdapter passiveAdapter;
    private final ChessPiece[] referenceCastling = new ChessPiece[2];
    private ChessPiece referenceEnPAssant = null;

    /**
     *
     * Constructor to initialize a Tile with its color, position, and size.
     *
     * @param chessPieceColors The color of the tile (BLACK or WHITE).
     * @param row                 The row number of the tile on the chessboard.
     * @param column              The column number of the tile on the chessboard.
     * @param size                The size (width and height) of the tile in pixels.
     */

    public Tile(ChessPieceColors chessPieceColors, int row, int column, int size) {
        this.tileColor = chessPieceColors;
        this.row = row;
        this.column = column;
        this.selfRefference = this;
        setUpFieldButtonProperties(size);
        setUpFieldButtonListeners();
    }

    // SET TILE.PIECE = piece
    public void setPiece(ChessPiece chessPiece) {
        if(chessPiece != null) {
            chessPiece.setHomeTile(this);
            this.chessPiece = chessPiece;
        }
        else {
            if(this.chessPiece!= null){
                this.chessPiece.setHomeTile(null);
                this.chessPiece = null;
            }
        }
    }


    public ChessPiece getPiece() {
        return chessPiece;
    }

    public JButton getButton() {
        return button;
    }

    // Getter and setter for coordinates.
    public int getY() {
        return row;
    }
    public int getX() {
        return column;
    }

    /** If chess piece = null - remove icon from button.
     * Else change icon to inhabitant of tile
     */
    public void refreshChessPieceIcon() {
        if (chessPiece != null && button != null) {
            BufferedImage chessPieceImage = chessPiece.getChessPieceVisuals();
            button.setIcon(new ImageIcon(chessPieceImage));
        } else if (button != null) {
            button.setIcon(null);
        }
    }

    /** Uses temporary SpecialTileColor to paint important buttons
     */
    public void paintButton(SpecTileFunc specialTileFunctions) {
        setInteractable(true);
        button.setBackground(specialTileFunctions.getColor());
    }

    /** Resets the tile's color to original.
     */
    public void resetButtonColor() {
        setInteractable(false);
        button.setBackground(tileColor.getTileColor());
    }


    /**
     */
    private void setUpFieldButtonProperties(int size) {
        button = new JButton();
        button.setLayout(new GridLayout(1, 1));
        button.setBounds(row * size, column * size, size, size);
        resetButtonColor();
        button.setVisible(true);
    }

    private void setUpFieldButtonListeners() {
        interactiveAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (chessPiece != null) {
                    GameOperator.getInstance().interactiveTileClicked(selfRefference);
                }
            }
        };
        passiveAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (chessPiece == null) {
                    GameOperator.getInstance().passiveTileClicked();
                }
            }
        };
    }

    /** Set's up passive or active mouse listener
     * @param b True if the tile should be interactable.
     */
    public void setInteractable(boolean b) {
        if (b) {
            // set mouse listener to handle active behaviour.
            for (MouseListener listener : button.getMouseListeners()) {
                // set mouse listener to handle passive behaviour
                button.removeMouseListener(listener);
            }
            button.addMouseListener(interactiveAdapter);
        } else {
            for (MouseListener listener : button.getMouseListeners()) {
                // set mouse listener to handle passive behaviour
                button.removeMouseListener(listener);
            }
            button.addMouseListener(passiveAdapter);
        }
    }

    /** Checks if this tile has any mouse listeners.
     * @return True if the tile has listeners; false otherwise.
     */
    public boolean hasListeners() {
        return button.getMouseListeners().length > 0;
    }

    // Adds and removes pieces from observers
    public void removeObserver(ChessPiece chessPiece) {
        try {
            if (chessPiece != null) {
                listOfObservers.remove(chessPiece);
            }
        } catch (Exception e) {
            System.err.println("Failed to remove observer: " + e.getMessage() +
                    "\nIt is possible that the observer is not assigned to the listOfObservers of this tile." +
                    "\nTile Details: Row=" + row + ", Column=" + column + ", Observers=" + listOfObservers);
        }
    }

    public void addObserver(ChessPiece chessPiece) {
        listOfObservers.add(chessPiece);
    }
    public List<ChessPiece> getListOfObservers() {
        return listOfObservers;
    }

    public boolean isSafeToStepOn(ChessPieceColors alliedColor) {
        for (ChessPiece observer : listOfObservers) {
            if (observer.getColor() != alliedColor) {
                return false;
            }
        }
        return true;
    }
    public void createCastlingOption(King king, Rook rook){
        referenceCastling[0] = king;
        referenceCastling[1]= rook;
    }

    public void removeCastlingOption(ChessPiece caller) {
        if (referenceCastling[0] != null) {
            referenceCastling[0].getImportantTiles().remove(this);
        }
        if (referenceCastling[1] != null) {
            referenceCastling[1].getImportantTiles().remove(this);
            if (caller instanceof King) {
                // add this tile as available tile for the rook
                referenceCastling[1]
                        .getImportantTiles()
                        .computeIfAbsent(SpecTileFunc.AVAILABLE_TILE, k -> new ArrayList<>())
                        .add(this);
            }
        }
    }
}
