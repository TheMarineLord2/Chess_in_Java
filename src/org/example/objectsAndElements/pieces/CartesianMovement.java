package org.example.objectsAndElements.pieces;

import org.example.objectsAndElements.ChessPieceColors;
import org.example.objectsAndElements.SpecTileFunc;
import org.example.objectsAndElements.chessboard.Chessboard;
import org.example.objectsAndElements.chessboard.Tile;
import org.example.objectsAndElements.pieces.pieceType.King;
import org.example.objectsAndElements.pieces.pieceType.Rook;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface CartesianMovement
{
    default void getPossibleMoves(boolean bishop, boolean rook, boolean pawn, boolean knight, boolean king, Tile homeTile, Chessboard chessboard, ChessPieceColors alliedColor, Map<SpecTileFunc, List<Tile>> importantTiles)
    {
        Point homeCoords = new Point(homeTile.getX(), homeTile.getY());
        importantTiles.get(SpecTileFunc.AVAILABLE_TILE).clear();
        importantTiles.get(SpecTileFunc.ENEMY_TILE).clear();
        importantTiles.get(SpecTileFunc.POTENTIALLY_OBSERVED).clear();


        if (bishop || rook|| knight)
        {
            getTilesInAllDirections(bishop, rook, knight, chessboard, alliedColor, homeCoords, importantTiles, 8);
        }
        if (pawn)
        {
            getPawnTiles(chessboard, alliedColor, homeCoords, importantTiles);
        }
        if (king)
        {
            if (!bishop)
            {
                getTilesInAllDirections(true, false, false, chessboard, alliedColor, homeCoords, importantTiles, 1);
            }
            if (!rook)
            {
                getTilesInAllDirections(false, true, false, chessboard, alliedColor, homeCoords, importantTiles, 1);
            }

            getTilesForCastling(alliedColor, homeCoords, chessboard, importantTiles);
            removeTilesForKingSafePassage(alliedColor, homeCoords, importantTiles);
        }

    }

    /** Get tiles pattern for every peace behaviour and call for each direction (4 at this moment)
     * @param range it's legal to customise the method using range as additional limiting border */
    private void getTilesInAllDirections(boolean bishop, boolean rook, boolean knight, Chessboard chessboard, ChessPieceColors alliedColor, Point homeCoords, Map<SpecTileFunc, List<Tile>> importantTiles, int range)
    {
        List<Tile> emptyTiles = importantTiles.get(SpecTileFunc.AVAILABLE_TILE);
        List<Tile> foeTiles = importantTiles.get(SpecTileFunc.ENEMY_TILE);
        for (int northEastSouthWest = 0; northEastSouthWest < 4; northEastSouthWest++) {
        int yDir, xDir, yDiag, xDiag;

        // for each direction and quarter
        switch (northEastSouthWest)
        {
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


            if (rook)
            {
                getTilesInDirection(chessboard, alliedColor, homeCoords, importantTiles, yDir, xDir, range);
            }
            if (bishop)
            {
                getTilesInDirection(chessboard, alliedColor, homeCoords, importantTiles, yDiag, xDiag, range);
            }
            if (knight)
            {
                getSpecificTilesFrom(chessboard, alliedColor, homeCoords, importantTiles.get(SpecTileFunc.AVAILABLE_TILE),importantTiles.get(SpecTileFunc.ENEMY_TILE), yDiag, xDiag, 1, 2);
                getSpecificTilesFrom(chessboard, alliedColor, homeCoords, importantTiles.get(SpecTileFunc.AVAILABLE_TILE),importantTiles.get(SpecTileFunc.ENEMY_TILE), yDiag, xDiag, 2, 1);
            }

        }
    }

    /** Get as many tiles in a specified direction until end of line of sight or board.*/
    private void getTilesInDirection(Chessboard chessboard, ChessPieceColors alliedColor, Point homeCoords, Map<SpecTileFunc, List<Tile>> importantTiles, int yDirection, int xDirection, int range)
    {
        boolean endOfLineOfSight = false;
        for (int i = 1; i <= range; i++)
        {
            // get N tile with offset :
            Tile tempTile = chessboard.getTile(homeCoords.x + i * xDirection, homeCoords.y + i * yDirection);

            if(tempTile == null)
            {
                // if tile outside boundaries
                break;
            }
            else if (endOfLineOfSight)
            {
                // if path of sight is blocked
                importantTiles.get(SpecTileFunc.POTENTIALLY_OBSERVED).add(tempTile);
            }
            else
            {
                if ( tempTile.getPiece() != null && tempTile.getPiece().getColor() == alliedColor)
                {
                    // if piece is allied
                    endOfLineOfSight = true;
                    importantTiles.get(SpecTileFunc.POTENTIALLY_OBSERVED).add(tempTile);
                }
                else
                {
                    // if tile empty or enemy
                    if (tempTile.getPiece() == null)
                    {
                        importantTiles.get(SpecTileFunc.AVAILABLE_TILE).add(tempTile);
                    }
                    else
                    {
                        importantTiles.get(SpecTileFunc.ENEMY_TILE).add(tempTile);
                        endOfLineOfSight = true;
                    }
                }
            }
        }

    }

    /** Get specific tile in relation with home tile*/
    private boolean getSpecificTilesFrom(Chessboard chessboard, ChessPieceColors alliedColor, Point homeCoords, List<Tile> emptyTiles, List<Tile> foeTiles, int yDir, Integer xDir, int yOffset, int xOffset)
    {
        Tile tempTile = chessboard.getTile(homeCoords.x + xOffset * xDir, homeCoords.y + yOffset * yDir);

        if (tempTile != null)
        {
            if (tempTile.getPiece() == null && emptyTiles != null)
            {
                // if tile empty
                emptyTiles.add(tempTile);
                return true;
            }
            else
            {
                if (tempTile.getPiece() != null && tempTile.getPiece().getColor() != alliedColor && foeTiles != null)
                {
                    // if tile occupied by enemy piece
                    foeTiles.add(tempTile);
                    return true;
                }
            }
        }

        // no tile found or not available
        return false;
    }

    /** to matrix specialTiles add second tile in a rook direction if king && rook not moved */
    private void getTilesForCastling(ChessPieceColors alliedColor, Point homeCoords, Chessboard chessboard, Map<SpecTileFunc, List<Tile>> importantTiles)
    {
        List<Tile> specialTiles = importantTiles.get(SpecTileFunc.SPECIAL_MOVE);
        Tile kingTile = chessboard.getTile(homeCoords.x, homeCoords.y);
        ChessPiece piece = chessboard.getTile(homeCoords.x, homeCoords.y).getPiece();

        if (piece.getClass() == King.class && piece.getMovesTaken() == 0 && chessboard.getTile(homeCoords.x, homeCoords.y).isSafeToStepOn(alliedColor))
        {
            // if king not moved and not threatened
            for (ChessPiece rook : chessboard.getAmountOfMaterial())
            {
                // every rook
                if (rook.getClass() == Rook.class && rook.getColor() == alliedColor && rook.getMovesTaken() == 0 )
                {
                    // if unmoved and allied
                    if (rook.getHomeTile().getY() == homeCoords.y )
                    {
                        // if Y axis is the same
                        // get tile 2x from King
                        int xOffset = 2* Integer.signum(homeCoords.x - rook.getHomeTile().getX());
                        Tile castlingTile = chessboard.getTile(xOffset + homeCoords.x, homeCoords.y);
                        specialTiles.add(castlingTile);
                        castlingTile.createCastlingOption((King) piece, (Rook) rook);
                    }
                    else if (rook.getHomeTile().getX() == homeCoords.x)
                    {
                        // if X axis is the same
                        // get tile 2x from King
                        int yOffset = 2 * Integer.signum(homeCoords.y - rook.getHomeTile().getY());
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

        if (alliedColor == ChessPieceColors.WHITE)
        {
            direction = -1;
        }
        else if (alliedColor == ChessPieceColors.BLACK)
        {
            direction = 1;
        }

        // check normal tile
        if(getSpecificTilesFrom(chessboard, alliedColor, homeCoords, importantTiles.get(SpecTileFunc.AVAILABLE_TILE), null, direction , 1,1, 0))
        {
            getSpecificTilesFrom(chessboard, alliedColor, homeCoords, importantTiles.get(SpecTileFunc.AVAILABLE_TILE), null,direction ,1,  2, 0);
        }

        // check for foeTiles
        getSpecificTilesFrom(chessboard, alliedColor, homeCoords, null, importantTiles.get(SpecTileFunc.ENEMY_TILE), direction, 1, 1, 1);
        getSpecificTilesFrom(chessboard, alliedColor, homeCoords, null, importantTiles.get(SpecTileFunc.ENEMY_TILE), direction, 1, 1, -1);
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
    private void removeTilesIfNotSafeToStepOrTravel(ChessPieceColors alliedColor, Point homeCoords, List<Tile> availableTiles, Point[] crossedOutDirections)
    {
        availableTiles.removeIf(tile ->
        {
            // get relative distance
            int xOffset = tile.getY() - homeCoords.x;
            int yOffset = tile.getX() - homeCoords.y;

            if (xOffset != yOffset && ! (xOffset == 0 || yOffset == 0) )
            {
                // if moves like a horse - returns if tile itself is safe
                return !tile.isSafeToStepOn(alliedColor);
            }
            else
            {
                // if magnitude is the same
                // normalize offset
                xOffset = Integer.signum(xOffset);
                yOffset = Integer.signum(yOffset);

                for (Point direction : crossedOutDirections)
                {
                    // Remove the tile if direction already crossed out
                    if (direction != null && direction.x == xOffset && direction.y == yOffset)
                    {

                        return true;
                    }
                }

                // if the direction is still available
                if (!tile.isSafeToStepOn(alliedColor))
                {
                    // if tile is not safe to step on
                    Point direction = new Point( xOffset, yOffset);
                    for (int i = 0; i < crossedOutDirections.length; i++)
                    {
                        // find next empty "direction"
                        if (crossedOutDirections[i] == null)
                        {
                            // add to excluded directions
                            crossedOutDirections[i] = direction;
                            break;
                        }
                    }

                    return true;
                }

                return false;
            }

        });

    }

    /** tiles close to home, closer */
    private void orderTilesByDistanceFromPoint(Point homeCoords, List<Tile> tiles) {
        tiles.sort((tile1, tile2) ->
        {
            double distance1 = Math.sqrt(Math.pow(tile1.getY() - homeCoords.x, 2) + Math.pow(tile1.getX() - homeCoords.y, 2));
            double distance2 = Math.sqrt(Math.pow(tile2.getY() - homeCoords.x, 2) + Math.pow(tile2.getX() - homeCoords.y, 2));

            return Double.compare(distance1, distance2);
        });

    }
    
}
