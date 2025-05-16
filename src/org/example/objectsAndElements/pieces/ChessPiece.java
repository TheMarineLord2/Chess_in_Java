package org.example.objectsAndElements.pieces;

import org.example.objectsAndElements.ChessPieceColors;
import org.example.objectsAndElements.SpecTileFunc;
import org.example.objectsAndElements.chessboard.Chessboard;
import org.example.objectsAndElements.chessboard.Tile;

import java.awt.image.BufferedImage;
import java.util.*;

public abstract class ChessPiece
{
    protected ChessPieceVisuals visualRepresentation;
    protected ChessPieceColors color;
    protected Map<SpecTileFunc, List<Tile>> importantTiles;
    protected Tile homeTile;
    protected Chessboard chessboard;
    protected int numbersOfMovesTaken = 0;
    
    protected ChessPiece(ChessPieceColors chessPieceColors, Chessboard chessboard)
    {
        this.color = chessPieceColors;
        this.chessboard = chessboard ;
        
        buildImportantTilesMap();
    }
    
    public void setVisualRepresentation(ChessPieceVisuals visualRepresentation) 
    {
        this.visualRepresentation = visualRepresentation;
        
    }
    
    public ChessPieceColors getColor()
    {
        return color;
        
    }
    
    public BufferedImage getChessPieceVisuals()
    {
        return visualRepresentation.getImage(this.color);
        
    }
    
    public Map<SpecTileFunc, List<Tile>> getImportantTiles()
    {
        lookAround();
        return importantTiles;
    }
    
    protected void buildImportantTilesMap() 
    {
        importantTiles = Map.of(
                SpecTileFunc.AVAILABLE_TILE, new ArrayList<>(), // Tiles where the piece can move
                SpecTileFunc.ENEMY_TILE, new ArrayList<>(), // Tiles occupied by enemy pieces
                SpecTileFunc.SPECIAL_MOVE, new ArrayList<>(), // Tiles for special moves (e.g., castling)
                SpecTileFunc.POTENTIALLY_OBSERVED, new ArrayList<>() // Tiles observed for potential actions
        );
    }

    protected void setImportantTiles(Map<SpecTileFunc, List<Tile>> tilesMap)
    {
        importantTiles = tilesMap;
        
    }
    
    public void setHomeTileVariable(Tile homeTile)
    {
        this.homeTile = homeTile;

    }

    public abstract void lookAround();
    
    
    public void setInteractableIfPossibleMoves(boolean b)
    {
        if (b && (!importantTiles.get(SpecTileFunc.AVAILABLE_TILE).isEmpty() || // Check if there are tiles to move
                !importantTiles.get(SpecTileFunc.ENEMY_TILE).isEmpty() || // Check if there are enemy tiles
                !importantTiles.get(SpecTileFunc.SPECIAL_MOVE).isEmpty())) { // Check for special move tiles

            homeTile.setInteractable(true); // Make home tile interactable
        } else {
            homeTile.setInteractable(false); // Disable interactivity
        }
    }

    public boolean hasAvailableMoves()
    {
        return (!importantTiles.get(SpecTileFunc.ENEMY_TILE).isEmpty() ||
                !importantTiles.get(SpecTileFunc.AVAILABLE_TILE).isEmpty());
    }
    
    public Tile getHomeTile()
    {
        return homeTile;

    }
    
    public void stopObservingImportantTiles()
    {
        for (SpecTileFunc key : importantTiles.keySet())
        { // Iterate over all categories of tiles
            List<Tile> tiles = importantTiles.get(key);
            if (tiles != null) 
            {
                Iterator<Tile> iterator = tiles.iterator();
                while (iterator.hasNext()) 
                {
                    Tile tile = iterator.next();
                    if (tile != null) 
                    {
                        tile.removeObserver(this); // Remove this chess piece as an observer
                        iterator.remove(); // Remove the tile from the list
                    }
                }
            }
        }
    }


    public void updateTileObservers() 
    {
        for (SpecTileFunc specTileFunc : importantTiles.keySet()) 
        {
            if(specTileFunc == SpecTileFunc.AVAILABLE_TILE || specTileFunc == SpecTileFunc.ENEMY_TILE || specTileFunc == SpecTileFunc.SPECIAL_MOVE)
            {
                List<Tile> tiles = importantTiles.get(specTileFunc);
                for(Tile tile : tiles)
                {
                    tile.addObserver(this);
                }
            }
        }
    }

    public int getMovesTaken()
    {
        return numbersOfMovesTaken;
        
    }

    public boolean isPinnedTo(Tile tile)
    {
        for (ChessPiece piece : homeTile.getListOfObservers())
        {
            // for each enemy looking at my tile
            if (piece.getColor() != this.color && piece.getImportantTiles().get(SpecTileFunc.ENEMY_TILE).contains(this.homeTile) && tile.getListOfObservers().contains(piece))
            {
                // if they potentially see the questioned location
                List<Tile> tilesBetween = chessboard.tilesInStraightLine(homeTile, tile);
                for (Tile t : tilesBetween)
                {
                    if (t.getPiece() != null && t.getPiece().getColor() == this.color)
                    {
                        return false; // An allied piece blocks the straight line
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{color=" + color + "}";
    }

}