package org.example.chessboardElements.chessboard;

import org.example.chessboardElements.AvaiableChessColors;
import org.example.chessboardElements.SpecialTileColors;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.mainControllers.gameControlls.GameOperator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
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
    private AvaiableChessColors tileColor;
    // The chess piece currently occupying this tile, or null if empty.
    private ChessPiece chessPiece;
    // The row and column coordinates of the tile on the chessboard.
    private int row, column;
    // A list of chess pieces observing this tile (used for special rules like attacks).
    private List<ChessPiece> listOfObservers = new java.util.ArrayList<>();
    // A MouseAdapter to handle mouse interactions on the tile.
    private MouseAdapter mouseAdapter;

    // ******************
    // Constructors
    // ******************

    /**
     * Constructor to initialize a Tile with its color, position, and size.
     *
     * @param avaiableChessColors The color of the tile (BLACK or WHITE).
     * @param row                 The row number of the tile on the chessboard.
     * @param column              The column number of the tile on the chessboard.
     * @param size                The size (width and height) of the tile in pixels.
     */
    public Tile(AvaiableChessColors avaiableChessColors, int row, int column, int size) {
        setTileColor(avaiableChessColors);
        this.row = row;
        this.column = column;
        setUpFieldButtonProperties(size);
        setUpFieldButtonListeners();
    }

    // metody publiczne
    /*public void changeLocationOfFieldButton(int x, int y){
        fieldButton.setLocation(x,y);
    }*/

    /**
     * Places a chess piece on this tile.
     *
     * @param chessPiece The chess piece to set on this tile.
     */
    public void setPiece(ChessPiece chessPiece) {
        chessPiece.setHomeTile(this);
        this.chessPiece = chessPiece;
    }

    /**
     * Removes the chess piece from this tile, if any.
     */
    public void removePiece() {
        chessPiece.setHomeTile(null);
        chessPiece = null;
    }

    /**
     * Retrieves the chess piece currently on this tile.
     *
     * @return The chess piece on this tile, or null if the tile is empty.
     */
    public ChessPiece getPiece() {
        return chessPiece;
    }
    // public AvaiableChessColors getOriginalColor(){return tileColor;}

    /**
     * Retrieves the JButton visual component representing this tile.
     *
     * @return The JButton representing this tile.
     */
    public JButton getButton() {
        return button;
    }

    /**
     * Updates the visual representation of the chess piece on this tile's button.
     * If the tile is empty, it removes any existing icon.
     */
    public void refreshTileIcon() {
        if (chessPiece != null && button != null) {
            BufferedImage chessPieceImage = chessPiece.getChessPieceVisuals();
            button.setIcon(new ImageIcon(chessPieceImage));
        } else if (button != null) {
            button.setIcon(null);
        }
    }

    /**
     * Highlights this tile with a special color, often used for valid moves or selections.
     *
     * @param specialTileColors The highlight color to set on this tile.
     */
    public void paintButton(SpecialTileColors specialTileColors) {
        setInteractable(true);
        button.setBackground(specialTileColors.getColor());
    }

    /**
     * Resets the tile's background color to its original color (BLACK or WHITE).
     */
    public void resetTileButtonColor() {
        setInteractable(false);
        button.setBackground(tileColor.getTileColor());
    }

    /**
     * Sets the original color of this tile.
     *
     * @param avaiableChessColors The color to assign to this tile (BLACK or WHITE).
     */
    private void setTileColor(AvaiableChessColors avaiableChessColors) {
        this.tileColor = avaiableChessColors;
    }

    /**
     * Sets up the button's default properties such as size, layout, and visibility.
     *
     * @param size The size of the tile in pixels (width and height).
     */
    private void setUpFieldButtonProperties(int size) {
        button = new JButton();
        button.setLayout(new GridLayout(1, 1));
        button.setBounds(row * size, column * size, size, size);
        resetTileButtonColor();
        button.setVisible(true);
    }

    /**
     * Configures listeners for mouse interactions on the button.
     * The listener calls a handler in the GameOperator when clicked.
     */
    private void setUpFieldButtonListeners() {
        mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (chessPiece != null) {
                    GameOperator.getInstance().clickedTile(row, column);
                }
                }
            };
        
    }

    /**
     * Enables or disables interactions with this tile.
     *
     * @param b True if the tile should be interactable; false otherwise.
     */
    public void setInteractable(boolean b) {
        if (b) {
            button.addMouseListener(mouseAdapter);
        } else {
            for (MouseListener listener : button.getMouseListeners()) {
                button.removeMouseListener(listener);
            }
        }
    }

    /**
     * Checks if this tile has any mouse listeners.
     *
     * @return True if the tile has listeners; false otherwise.
     */
    public boolean hasListeners() {
        return button.getMouseListeners().length > 0;
    }

    /**
     * Gets the row position of this tile on the chessboard.
     *
     * @return The row position of the tile.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column position of this tile on the chessboard.
     *
     * @return The column position of the tile.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Removes a chess piece from the list of observers for this tile.
     *
     * @param chessPiece The chess piece to remove from the observer list.
     */
    public void removeObserver(ChessPiece chessPiece) {
        listOfObservers.remove(chessPiece);
    }

    /**
     * Adds the current chess piece on this tile to the list of observers.
     */
    public void addObserver() {
        listOfObservers.add(chessPiece);
    }
}
