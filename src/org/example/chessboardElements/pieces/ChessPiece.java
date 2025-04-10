package org.example.chessboardElements.pieces;

import org.example.chessboardElements.AvaiableChessColors;
import org.example.chessboardElements.SpecialTileColors;
import org.example.chessboardElements.chessboard.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public abstract class ChessPiece {
    protected ChessPieceVisuals visualRepresentation;
    protected AvaiableChessColors color;
    protected Map<SpecialTileColors, Tile[] > importantTiles;
    protected int lastMoveTurn = 0;

    // updates tilesSeen and tilesUnavaiable
    // implements move pattern of every piece.
    //abstract void lookAtEveryPossibleSpace();
    //

    protected ChessPiece(AvaiableChessColors avaiableChessColors) {
        this.color = avaiableChessColors;
        initiateImportantTilesMap();
        refreshImportantTiles();
    }
    // getters and setters
    public void setVisualRepresentation(ChessPieceVisuals visualRepresentation) {
        this.visualRepresentation = visualRepresentation;
    }
    public AvaiableChessColors getColor() {
        return color;
    }
    public BufferedImage getChessPieceVisuals() {
        return visualRepresentation.getImage(this.color);
    }
    public Map<SpecialTileColors, Tile[]> getImportantTiles() {
        refreshImportantTiles();
        return importantTiles;
    }

    protected void refreshImportantTiles() {
        lookAround();
        // updateAvaiableTiles();
        // updateEnemyTiles();
        // updateSpecialMoveTiles();
    }
    protected void initiateImportantTilesMap() {
        if (importantTiles == null) {
            importantTiles = Map.of(
                    SpecialTileColors.HOME_TILE, null,
                    SpecialTileColors.AVAILABLE_TILE, null,
                    SpecialTileColors.ENEMY_TILE, null,
                    SpecialTileColors.SPECIAL_MOVE, null
            );
        }
    }
    public void setHomeTile(Tile homeTile) {
        importantTiles.put(SpecialTileColors.HOME_TILE,new Tile[]{homeTile});
    }
    protected void setAvaiableTiles(){
        importantTiles.put(SpecialTileColors.AVAILABLE_TILE, null);
    }
    protected void setEnemyTiles(){
        importantTiles.put(SpecialTileColors.ENEMY_TILE, null);
    }
    protected void setSpecialMoveTiles(){
        importantTiles.put(SpecialTileColors.SPECIAL_MOVE, null);
    }
    protected void lookAround(){
    }

    public void setInteractableIfPossibleMoves(boolean b) {
        if (b && (importantTiles.get(SpecialTileColors.AVAILABLE_TILE).length > 0 ||
                importantTiles.get(SpecialTileColors.ENEMY_TILE).length > 0 ||
                importantTiles.get(SpecialTileColors.SPECIAL_MOVE).length > 0) ){
            importantTiles.get(SpecialTileColors.HOME_TILE)[0].setInteractable(b);
        }
        else{
            importantTiles.get(SpecialTileColors.HOME_TILE)[0].setInteractable(false);
        }
    }

    public boolean isTrulyInteractable() {
        return importantTiles.get(SpecialTileColors.HOME_TILE)[0].hasListeners();
    }

    public Point getHomeTile() {
        return new Point()
    }

    public void stopObservingCurrentTiles() {
        for (Tile[] tiles : importantTiles.values()) {
            if (tiles != null) {
                for (Tile tile : tiles) {
                    if (tile != null) {
                        tile.stopObserving();
                    }
                }
            }
        }
    }
}