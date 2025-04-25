package org.example.chessboardElements.pieces;

import org.example.chessboardElements.ChessPieceColors;
import org.example.chessboardElements.SpecTileFunc;
import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.chessboard.Tile;
import org.example.chessboardElements.pieces.pieceType.King;
import org.example.chessboardElements.pieces.pieceType.Rook;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface CartesianMovement {
    default void getPossibleMoves(boolean bishop, boolean rook, boolean pawn, boolean knight, boolean king,
             Chessboard chessboard, ChessPieceColors alliedColor, Map<SpecTileFunc, List<Tile>> importantTiles) {
        // prepare important tiles list for new elements and create faster access for home tile coords
        System.out.print(importantTiles.get(SpecTileFunc.HOME_TILE));
        Point homeCoords = new Point(importantTiles.get(SpecTileFunc.HOME_TILE).getFirst().getX(),importantTiles.get(SpecTileFunc.HOME_TILE).getFirst().getY());
        importantTiles.get(SpecTileFunc.AVAILABLE_TILE).clear();
        importantTiles.get(SpecTileFunc.ENEMY_TILE).clear();
        importantTiles.get(SpecTileFunc.POTENTIALLY_OBSERVED).clear();
        if(bishop || rook|| knight){    /* for Queen, it works well */
            getTilesInAllDirections(bishop, rook, knight, chessboard, alliedColor, homeCoords, importantTiles, 8); }
        if (pawn) {
            getPawnTiles(chessboard, alliedColor, homeCoords, importantTiles); }
        if(king){
            if(!bishop){getTilesInAllDirections(true, false, false, chessboard, alliedColor, homeCoords, importantTiles, 1);}
            if(!rook){getTilesInAllDirections(false, true, false, chessboard, alliedColor, homeCoords, importantTiles, 1);}
            getTilesForCastling(alliedColor, homeCoords, chessboard, importantTiles);
            removeTilesForKingSafePassage(alliedColor, homeCoords, importantTiles);
        }
        
    }

    /** Get tiles pattern for every peace behaviour and call for each direction (4 at this moment)
     * @param range it's legal to customise the method using range as additional limiting border */
    private void getTilesInAllDirections(boolean bishop, boolean rook, boolean knight, Chessboard chessboard,
                                         ChessPieceColors alliedColor, Point homeCoords, Map<SpecTileFunc, List<Tile>> importantTiles, int range) {
        List<Tile> emptyTiles = importantTiles.get(SpecTileFunc.AVAILABLE_TILE);
        List<Tile> foeTiles = importantTiles.get(SpecTileFunc.ENEMY_TILE);
        for (int northEastSouthWest = 0; northEastSouthWest < 4; northEastSouthWest++) {
        int yDir, xDir, yDiag, xDiag;

        // for each direction and quarter
        switch (northEastSouthWest) {
            case 0: // north / 1sr qv
                yDir = 1;
                xDir = 0;
                yDiag = 1;
                xDiag = 1;
                break;
            case 1: // east / 2nd qv
                yDir = 0;
                xDir = 1;
                yDiag = 1;
                xDiag = -1;
                break;
            case 2: // south / 3rd qv
                yDir = -1;
                xDir = 0;
                yDiag = -1;
                xDiag = -1;
                break;
            case 3: // west / 4th qv
                yDir = 0;
                xDir = -1;
                yDiag = -1;
                xDiag = 1;
                break;
            default:
                yDir = 0;
                xDir = 0;
                xDiag = 0;
                yDiag = 0;
        }
            if (rook) {
                getTilesInDirection(chessboard, alliedColor, homeCoords, importantTiles, yDir, xDir, range);
            }
            if (bishop) {
                getTilesInDirection(chessboard, alliedColor, homeCoords, importantTiles, yDiag, xDiag, range);
            }
            if (knight) {
                getSpecificTilesFrom(chessboard, alliedColor, homeCoords, importantTiles.get(SpecTileFunc.AVAILABLE_TILE),importantTiles.get(SpecTileFunc.ENEMY_TILE), yDiag, xDiag, 1, 2);
                getSpecificTilesFrom(chessboard, alliedColor, homeCoords, importantTiles.get(SpecTileFunc.AVAILABLE_TILE),importantTiles.get(SpecTileFunc.ENEMY_TILE), yDiag, xDiag, 2, 1);
            }
        }
    }

    /** Get as many tiles in a specified direction until end of line of sight or board.*/
    private void getTilesInDirection(Chessboard chessboard, ChessPieceColors alliedColor, Point homeCoords, Map<SpecTileFunc, List<Tile>> importantTiles, int yDirection, int xDirection, int range) {
        List<Tile> emptyTiles = importantTiles.get(SpecTileFunc.AVAILABLE_TILE);
        List<Tile> foeTiles = importantTiles.get(SpecTileFunc.ENEMY_TILE);
        boolean endOfLineOfSight = false;
        for (int i = 1; i <= range; i++) {
            Tile tempTile = chessboard.getTile(homeCoords.x + i * xDirection, homeCoords.y + i * yDirection);
            // if tile is null
            if (tempTile == null ||
                    /* or tile has a piece, but an allied one*/
                    (tempTile.getPiece() != null && tempTile.getPiece().getColor() == alliedColor)) {
                endOfLineOfSight = true;

            } /* if tile empty or enemy */
            else {
                // tile available
                if (tempTile.getPiece() == null) {
                    emptyTiles.add(tempTile);
                }/* tile capturable */
                else {
                    foeTiles.add(tempTile);
                    endOfLineOfSight = true;
                }
            }


        }
    }

    /** Reaches out ignoring line of sight to a singular tile.
     * @param xOffset how much to the left or right.
     * @param yOffset how far looking straight.
     * @param xDir nullable if we are not caring to turn around.
     * @param yDir up n down direction. Must be assigned.
     * @param emptyTiles null, if we are not seeking peace.
     * @param foeTiles null, if we are not seeking war.
     */
    private boolean getSpecificTilesFrom(Chessboard chessboard, ChessPieceColors alliedColor, Point homeCoords, List<Tile> emptyTiles, List<Tile> foeTiles, int yDir, Integer xDir, int yOffset, int xOffset) {
        if (xDir == null){ xDir=1; }
        Tile tempTile = chessboard.getTile(homeCoords.x + xOffset * xDir, homeCoords.y + yOffset * yDir);
        // if tile exists, reach.
        if (tempTile != null) {
            // if empty, and we are checking empty tiles (list provided)
            if (tempTile.getPiece() == null && emptyTiles != null) {
                emptyTiles.add(tempTile);
                return true;
            } else {
                if (tempTile.getPiece().getColor() != alliedColor && foeTiles != null) {
                    foeTiles.add(tempTile);
                    return true;
                }
            }
        }
        return false;
    }

    /** to matrix specialTiles add second tile in a rook direction if king && rook not moved */
    private void getTilesForCastling(ChessPieceColors alliedColor, Point homeCoords, Chessboard chessboard, Map<SpecTileFunc, List<Tile>> importantTiles) {
        System.out.println("King tile: " + homeCoords.x + " " + homeCoords.y);
        List<Tile> specialTiles = importantTiles.get(SpecTileFunc.SPECIAL_MOVE);
        Tile kingTile = chessboard.getTile(homeCoords.x, homeCoords.y);
        ChessPiece piece = chessboard.getTile(homeCoords.x, homeCoords.y).getPiece();
        // if king not moved
        if (piece.getClass() == King.class
                && piece.getMovesTaken() == 0
                && chessboard.getTile(homeCoords.x, homeCoords.y).isSafeToStepOn(alliedColor)) {
            // every rook
            for(ChessPiece rook : chessboard.getAmountOfMaterial()){
                // check unmoved rooks.
                if(rook.getClass() == Rook.class && rook.getColor() == alliedColor && rook.getMovesTaken() == 0 ){
                    // if same Y
                    if(rook.getHomeTile().getY() == homeCoords.y ){
                        int xOffset = 2* Integer.signum(homeCoords.x - rook.getHomeTile().getX());
                        Tile castlingTile = chessboard.getTile(xOffset + homeCoords.x, homeCoords.y);
                        specialTiles.add(castlingTile);
                        castlingTile.createCastlingOption((King) piece, (Rook) rook);
                    }else /* if same X */ if(rook.getHomeTile().getX() == homeCoords.x){
                        int yOffset = 2* Integer.signum(homeCoords.y - rook.getHomeTile().getY());
                        Tile castlingTile = chessboard.getTile(homeCoords.x, homeCoords.y + yOffset);
                        specialTiles.add(castlingTile);
                        castlingTile.createCastlingOption((King) piece, (Rook) rook);
                    }
                }
            }
        }
    }

    /** for alliedColor, pick direction, and:
     * Always try to get the tile ahead.
     * Check if there are diagonal foes
     * See if there are en passant ones
     * If available is in last row, allow to promote */
    private void getPawnTiles(Chessboard chessboard, ChessPieceColors alliedColor, Point homeCoords, Map<SpecTileFunc, List<Tile>> importantTiles) {
        int direction = 0;
        // because we are drawing chessboard from top left corner:
        if(alliedColor == ChessPieceColors.WHITE){ direction = -1; }
        else if(alliedColor == ChessPieceColors.BLACK){ direction = 1; }

        // check normal tile
        if(getSpecificTilesFrom(chessboard, alliedColor, homeCoords,  importantTiles.get(SpecTileFunc.AVAILABLE_TILE), null, direction, null,1, 0)){
            getSpecificTilesFrom(chessboard, alliedColor, homeCoords, importantTiles.get(SpecTileFunc.ENEMY_TILE), null, direction, null, 2, 0);
        }
        // check for foeTiles
        getSpecificTilesFrom(chessboard, alliedColor, homeCoords, null, importantTiles.get(SpecTileFunc.ENEMY_TILE), direction, null, 1, 1);
        getSpecificTilesFrom(chessboard, alliedColor, homeCoords, null, importantTiles.get(SpecTileFunc.ENEMY_TILE), direction, null, 1, -1);
    }

    /** remove threatened tiles or the one with threatened road */
    private void removeTilesForKingSafePassage(ChessPieceColors alliedColor, Point homeCoords, Map<SpecTileFunc, List<Tile>> importantTiles) {
        List<Tile> emptyTiles = importantTiles.get(SpecTileFunc.AVAILABLE_TILE);
        List<Tile> foeTiles = importantTiles.get(SpecTileFunc.ENEMY_TILE);
        List<Tile> specialTiles = importantTiles.get(SpecTileFunc.SPECIAL_MOVE);

        orderTilesByDistanceFromPoint(homeCoords, emptyTiles);
        orderTilesByDistanceFromPoint(homeCoords, foeTiles);

        Point[] crossedOutDirections = new Point[8];
        Arrays.fill(crossedOutDirections, null);

        removeTilesIfNotSafeToStepOrTravel(alliedColor,homeCoords,emptyTiles, crossedOutDirections);
        removeTilesIfNotSafeToStepOrTravel(alliedColor,homeCoords,foeTiles, crossedOutDirections);
        removeTilesIfNotSafeToStepOrTravel(alliedColor,homeCoords,specialTiles, crossedOutDirections);
    }

    /** Remove threatened tiles from a specific table */
    private void removeTilesIfNotSafeToStepOrTravel(ChessPieceColors alliedColor, Point homeCoords, List<Tile> availableTiles, Point[] crossedOutDirections) {
        // for every element in availableTiles
        availableTiles.removeIf(tile -> {
            // Check if the tile is in a specific direction from the home tile
            int xOffset = tile.getY() - homeCoords.x;
            int yOffset = tile.getX() - homeCoords.y;

            // if move as a horsey
            if (xOffset != yOffset && ! (xOffset == 0 || yOffset == 0) ) {
                // true == remove if unsafe
                return !tile.isSafeToStepOn(alliedColor);
            }
            else{
                // normalize offset to general directions
                xOffset = Integer.signum(xOffset);
                yOffset = Integer.signum(yOffset);

                // Remove the tile if direction already crossed out
                for (Point direction : crossedOutDirections) {
                    if (direction != null && direction.x == xOffset && direction.y == yOffset) {
                        // true == remove if unsafe
                        return true;
                    }
                }

                boolean isSafeToStepOn = tile.isSafeToStepOn(alliedColor);
                if (!isSafeToStepOn) {
                    // add to excluded directions
                    Point direction = new Point( xOffset, yOffset);
                    for (int i = 0; i < crossedOutDirections.length; i++) {
                        if (crossedOutDirections[i] == null) {
                            crossedOutDirections[i] = direction;
                            break;
                        }
                    }
                }
                // true == remove if unsafe
                return !isSafeToStepOn;
            }
        });
    }

    /** tiles close to home, closer */
    private void orderTilesByDistanceFromPoint(Point homeCoords, List<Tile> tiles) {
        tiles.sort((tile1, tile2) -> {
            double distance1 = Math.sqrt(Math.pow(tile1.getY() - homeCoords.x, 2) + Math.pow(tile1.getX() - homeCoords.y, 2));
            double distance2 = Math.sqrt(Math.pow(tile2.getY() - homeCoords.x, 2) + Math.pow(tile2.getX() - homeCoords.y, 2));
            return Double.compare(distance1, distance2);
        });
    }
}
