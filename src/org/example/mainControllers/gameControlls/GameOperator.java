package org.example.mainControllers.gameControlls;

import org.example.objectsAndElements.SpecTileFunc;
import org.example.objectsAndElements.chessboard.Chessboard;
import org.example.objectsAndElements.chessboard.Tile;
import org.example.objectsAndElements.pieces.ChessPiece;
import org.example.objectsAndElements.pieces.pieceType.King;
import org.example.objectsAndElements.pieces.pieceType.Pawn;
import org.example.mainControllers.mainScreen.MainOperator;
import org.example.mainControllers.mainScreen.MainWindowFrame;
import org.example.objectsAndElements.pieces.pieceType.Rook;

import java.util.List;
import java.util.Map;


public class GameOperator implements Runnable {
    private static GameOperator gameOperatorInstance = null;
    private GameScreenFactory gameScreenFactory;
    private final GameInstance gameInstance;
    private final MainWindowFrame mainWindowFrame;
    private ChessPiece selectedPiece = null;
    private Chessboard chessboard = null;
    private Map<SpecTileFunc, List<Tile>> importantTiles = null;


    public GameOperator(MainOperator caller, Player player1, Player player2)
    {
        gameOperatorInstance = this;
        this.mainWindowFrame = caller.getMainWindowFrame();
        gameInstance = new GameInstance(player1, player2, GameScreenFactory.getTileSize());
    }

    public GameInstance getGameInstance()
    {
        return gameInstance;
    }
    public MainWindowFrame getMainWindowFrame()
    {
        return mainWindowFrame;
    }

    public static GameOperator getGameOperatorInstance()
    {
        if (gameOperatorInstance == null)
        {
            throw new IllegalStateException("GameOperator instance is null. Cannot access it.");
        }
        return gameOperatorInstance;
    }

    @Override
    public void run(){
        gameScreenFactory = new GameScreenFactory(gameInstance, mainWindowFrame);
        chessboard = gameInstance.getChessboard();
    }


    public void tileClicked(Tile tile) {
        try
        {
            if (selectedPiece == null)
            {
                // if no piece in hand
                selectedPiece = tile.getPiece();
                if (selectedPiece != null && selectedPiece.hasAvailableMoves() && selectedPiece.getColor() == gameInstance.getCurrentPlayerColor() )
                {
                    // and if picked up the piece of current's player color
                    // Set the piece visuals as the cursor representation
                    gameScreenFactory.setImageAsCursor(selectedPiece.getChessPieceVisuals());
                    // Show possible moves for the selected piece
                    importantTiles = selectedPiece.getImportantTiles();
                    gameScreenFactory.paintSpecialTiles(importantTiles);
                }
                else
                {
                    selectedPiece = null;
                }
            }
            else
            {
                // if there's a piece in hand
                if (importantTiles != null)
                {
                    switch (checkSpecTileFunc(tile))
                    {
                        case AVAILABLE_TILE -> {
                            moveToTile(tile);
                            gameInstance.takeATurn();
                        }
                        case ENEMY_TILE -> {
                            captureTile(tile);
                            gameInstance.takeATurn();
                        }
                        case SPECIAL_MOVE -> {
                            specialMove(tile);
                            gameInstance.takeATurn();
                        }
                        case null -> dropPiece();
                        default -> dropPiece();
                    }
                    gameScreenFactory.resetColors();
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("An error occurred: " + e.getMessage());
        }
        System.out.println("pong! - " + selectedPiece);
    }


    private void moveToTile(Tile tile)
    {
        if (tile.getPiece() != null)
        {
            captureTile(tile);
        }
        else
        {
            takeSelectedPieceFromItsHomeTile();
            putSelectedPieceOnTargetTile(tile);
        }
    }

    private void takeSelectedPieceFromItsHomeTile()
    {
        Tile homeTile = selectedPiece.getHomeTile();

        homeTile.setPiece(null);
        homeTile.refreshChessPieceIcon();

        selectedPiece.stopObservingImportantTiles();
        updateObserversOfATile(homeTile);
    }

    private void putSelectedPieceOnTargetTile(Tile tile){
        tile.setPiece(selectedPiece);
        tile.refreshChessPieceIcon();

        selectedPiece.lookAround();
        updateObserversOfATile(tile);

        selectedPiece.incrementNumberOfMovesTaken();
        selectedPiece = null;

        gameScreenFactory.resetToDefaultCursor();
        gameScreenFactory.resetColors();
    }

    private void specialMove(Tile tile)
    {
        if (selectedPiece.getClass()== King.class)
        {
            // if we do castling
            Rook rook = tile.getRookForCastling();
            // Determine the direction of the tile relative to the king tile
            Tile kingTile = selectedPiece.getHomeTile();
            int dx = tile.getX() - kingTile.getX();
            int dy = tile.getY() - kingTile.getY();

            if (dx == 0 && dy < 0)
            {
                takeRookFromHomeTileAndMoveItThere(rook, chessboard.getTile(kingTile.getX(), kingTile.getY() - 1));
                System.out.println("Tile is on the left of the king.");
            }
            else if (dx == 0 && dy > 0)
            {
                takeRookFromHomeTileAndMoveItThere(rook, chessboard.getTile(kingTile.getX(), kingTile.getY() + 1));
                System.out.println("Tile is on the right of the king.");
            }
            else if (dy == 0 && dx < 0)
            {
                takeRookFromHomeTileAndMoveItThere(rook, chessboard.getTile(kingTile.getX() - 1, kingTile.getY()));
                System.out.println("Tile is below the king.");
            }
            else if (dy == 0 && dx > 0)
            {
                takeRookFromHomeTileAndMoveItThere(rook, chessboard.getTile(kingTile.getX() + 1, kingTile.getY()));
                System.out.println("Tile is above the king.");
            }

            // king is still in hand
            moveToTile(tile);
        }
    }

    private void takeRookFromHomeTileAndMoveItThere(Rook rook, Tile targetTile)
    {
        Tile homeTile = rook.getHomeTile();

        // pick it
        homeTile.setPiece(null);
        homeTile.refreshChessPieceIcon();

        rook.stopObservingImportantTiles();
        updateObserversOfATile(homeTile);

        // drop it
        targetTile.setPiece(rook);
        targetTile.refreshChessPieceIcon();

        rook.lookAround();
        updateObserversOfATile(targetTile);

        rook.incrementNumberOfMovesTaken();
    }

    private void captureTile(Tile tile)
    {
        if( tile.getPiece() != null && tile.getPiece().getColor() != selectedPiece.getColor() )
        {
            // if there is enemy piece on that tile
            takeSelectedPieceFromItsHomeTile();

            // Remove enemy piece
            ChessPiece capturedPiece = chessboard.removePieceFromMaterial(tile.getPiece());
            capturedPiece.stopObservingImportantTiles();
            gameInstance.getPlayerObject(capturedPiece.getColor()).addToGraveyard(capturedPiece);

            // Put new piece instead
            putSelectedPieceOnTargetTile(tile);
        }
        else
        {
            throw IllegalArgumentException.class.cast("Trying to capture empty or an allied tile");
        }
    }

    private void updateObserversOfATile(Tile homeTile)
    {
        for (ChessPiece observer : homeTile.getListOfObservers())
        {
            observer.lookAround();
        }
    }

    private void dropPiece()
    {
        gameScreenFactory.resetColors();

        selectedPiece = null;
        importantTiles = null;
    }

    /** returns the key if tile exists in important tiles, or null if not */
    private SpecTileFunc checkSpecTileFunc(Tile tile)
    {
        for (Map.Entry<SpecTileFunc, List<Tile>> entry : importantTiles.entrySet())
        {
            List<Tile> tiles = entry.getValue(); // Extract the Tile[] array for the key
            if (tiles != null)
            {
                for (Tile t : tiles)
                {
                    // Iterate through each Tile in the array
                    if (t.equals(tile))
                    {
                        // Check if the current Tile matches the given Tile
                        return entry.getKey(); // Return the corresponding key
                    }
                }
            }
        }
        return null; // Return null if no match is found
    }

    public void showEndGameMessage(String message)
    {
        gameScreenFactory.showEndgameMessage(message);
    }
}