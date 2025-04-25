package org.example.chessboardElements.pieces;

import org.example.chessboardElements.ChessPieceColors;
import org.example.chessboardElements.SpecTileFunc;
import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.chessboard.Tile;

import java.awt.image.BufferedImage;
import java.util.*;

public abstract class ChessPiece{
    // Visual representation of the chess piece
    protected ChessPieceVisuals visualRepresentation;
    // Color of the chess piece
    protected ChessPieceColors color;
    // Map of special tiles relevant to the chess piece's actions
    protected Map<SpecTileFunc, List<Tile>> importantTiles;

    protected Chessboard chessboard;
    protected int numbersOfMovesTaken = 0;

    // Constructor to initialize the chess piece with a given color
    protected ChessPiece(ChessPieceColors chessPieceColors, Chessboard chessboard) {
        this.color = chessPieceColors;
        this.chessboard = chessboard ;
        // Initialize the map of important tiles
        buildImportantTilesMap();
    }

    // Set the visual representation of the chess piece
    public void setVisualRepresentation(ChessPieceVisuals visualRepresentation) {
        this.visualRepresentation = visualRepresentation;
    }

    // Get the color of the chess piece
    public ChessPieceColors getColor() {
        return color;
    }

    // Get the visual representation of the chess piece as a BufferedImage
    public BufferedImage getChessPieceVisuals() {
        return visualRepresentation.getImage(this.color);
    }

    // Get the current map of important tiles
    public Map<SpecTileFunc, List<Tile>> getImportantTiles() {
        // Ensure the tiles are refreshed before retrieving them
        lookAround();
        return importantTiles;
    }

    /**
     * Initialize the map with default values for special tile categories
     */
    protected void buildImportantTilesMap() {
        importantTiles = Map.of(
                SpecTileFunc.HOME_TILE, new ArrayList<>(),
                SpecTileFunc.AVAILABLE_TILE, new ArrayList<>(), // Tiles where the piece can move
                SpecTileFunc.ENEMY_TILE, new ArrayList<>(), // Tiles occupied by enemy pieces
                SpecTileFunc.SPECIAL_MOVE, new ArrayList<>(), // Tiles for special moves (e.g., castling)
                SpecTileFunc.POTENTIALLY_OBSERVED, new ArrayList<>() // Tiles observed for potential actions
        );
    }

    // Set the home tile of the chess piece
    public void setHomeTile(Tile homeTile) {
        // If there is already a list under HOME_TILE, clear it
        if(importantTiles.get(SpecTileFunc.HOME_TILE)!= null){
            importantTiles.get(SpecTileFunc.HOME_TILE).clear();
        }
        importantTiles.get(SpecTileFunc.HOME_TILE).add(homeTile);
        System.out.println(this.getClass().getSimpleName());
    }

    public abstract void lookAround();

    // Enable or disable interactivity of the home tile based on available moves
    public void setInteractableIfPossibleMoves(boolean b) {
        if (b && (!importantTiles.get(SpecTileFunc.AVAILABLE_TILE).isEmpty() || // Check if there are tiles to move
                !importantTiles.get(SpecTileFunc.ENEMY_TILE).isEmpty() || // Check if there are enemy tiles
                !importantTiles.get(SpecTileFunc.SPECIAL_MOVE).isEmpty())) { // Check for special move tiles

            importantTiles.get(SpecTileFunc.HOME_TILE).getFirst().setInteractable(true); // Make home tile interactable
        } else {
            importantTiles.get(SpecTileFunc.HOME_TILE).getFirst().setInteractable(false); // Disable interactivity
        }
    }

    public boolean hasAvailableMoves() {
        return importantTiles != null ;
    }

    /** @return Point x y
     */
    public Tile getHomeTile() {
        return importantTiles.get(SpecTileFunc.HOME_TILE).getFirst();
    }

    /** for EACH tile calls tile.removeObserver, then removes tile from importantTiles */
    public void stopObservingImportantTiles() {
        for (SpecTileFunc key : importantTiles.keySet()) { // Iterate over all categories of tiles
            List<Tile> tiles = importantTiles.get(key);
            if (tiles != null) {
                Iterator<Tile> iterator = tiles.iterator();
                while (iterator.hasNext()) {
                    Tile tile = iterator.next();
                    if (tile != null) {
                        tile.removeObserver(this); // Remove this chess piece as an observer
                        iterator.remove(); // Remove the tile from the list
                    }
                }
            }
        }
    }

    /** set's up chess piece as observer fot avaiable, Foe, SpecialTiles */
    public void updateTileObservers() {
        for (SpecTileFunc specTileFunc : importantTiles.keySet()) { // Iterate over all categories of important tiles
            if(specTileFunc == SpecTileFunc.AVAILABLE_TILE
                    || specTileFunc == SpecTileFunc.ENEMY_TILE
                    || specTileFunc == SpecTileFunc.SPECIAL_MOVE){
                List<Tile> tiles = importantTiles.get(specTileFunc);
                for(Tile tile : tiles){
                    tile.addObserver(this);
                }
            }
        }
    }

    public int getMovesTaken() {
        return numbersOfMovesTaken;
    }
}