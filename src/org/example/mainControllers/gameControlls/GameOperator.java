package org.example.mainControllers.gameControlls;

import org.example.chessboardElements.SpecTileFunc;
import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.chessboard.Tile;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.pieces.pieceType.King;
import org.example.chessboardElements.pieces.pieceType.Pawn;
import org.example.mainControllers.mainScreen.MainOperator;
import org.example.mainControllers.mainScreen.MainWindowFrame;

import java.util.List;
import java.util.Map;

/**
 * The GameOperator class handles the main logic and control flow of the chess game.
 * This includes managing the game state, piece selection, and user interactions with the chessboard.
 */
public class GameOperator implements Runnable {
    private static GameOperator gameOperatorInstance = null;
    private GameScreenFactory gameScreenFactory;
    private GameInstance gameInstance;
    private final MainWindowFrame mainWindowFrame;
    private final MainOperator mainOperator;
    private ChessPiece selectedPiece = null;
    private Chessboard chessboard = null;
    private Map<SpecTileFunc, List<Tile>> importantTiles = null;

    /** Private constructor for the singleton design pattern. */
    public GameOperator(MainOperator caller) {
        gameOperatorInstance = this;
        mainOperator = caller;
        this.mainWindowFrame = mainOperator.getMainWindowFrame();
    }
    @Override
    public void run() {
        CreateGame(mainOperator.getPlayer1(), mainOperator.getPlayer2());
        while(gameInstance.isGameOver()) { gameInstance.takeATurn();}
    }
    public GameInstance getGameInstance(){
        return gameInstance;
    }
    public MainWindowFrame getMainWindowFrame(){
        return mainWindowFrame;
    }

    public static GameOperator getGameOperatorInstance() {
        if (gameOperatorInstance == null) {
            throw new IllegalStateException("GameOperator instance is null. Cannot access it.");
        }
        return gameOperatorInstance;
    }

    /** Handles 4 situations. Those will be proteced in another way too
     *
     * 1. Clicks a tile without a piece in hand.
     * 2. Then get piece and check if piece is already got (not null)
     * 3. piece in hand and clicked tile
     * 4. check if it is important one
     *
     * @param tile object by which this method was called.
     */
    public void interactiveTileClicked(Tile tile) {
        try {
            if (selectedPiece == null) {
                selectedPiece = tile.getPiece();
                
                if (selectedPiece != null) {
                    // make sure that King moves are up to date
                    if (selectedPiece.getClass() == King.class) {
                        for (ChessPiece piece : chessboard.getAmountOfMaterial()) {
                            if (piece.getColor() == selectedPiece.getColor() && piece instanceof King ) {
                                piece.lookAround();
                                break;
                            }
                        }
                    }
                    // Set the piece visuals as the cursor representation
                    gameScreenFactory.setImageAsCursor(selectedPiece.getChessPieceVisuals());
                    // Show possible moves for the selected piece
                    importantTiles = selectedPiece.getImportantTiles();
                    gameScreenFactory.paintSpecialTiles(importantTiles);
                }
            } else {
                SpecTileFunc color = checkSpecTileFunc(tile);
                if(color != null && color != SpecTileFunc.POTENTIALLY_OBSERVED){
                    movePiece(tile, color);
                }
                else {
                    dropPiece();
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void passiveTileClicked() {
        if(selectedPiece!=null){
            dropPiece();
        }
    }

    public void rightMouseClicked(){
        dropPiece();
    }

    private void movePiece(Tile tile, SpecTileFunc color) {
        switch (color) {
            case ENEMY_TILE:
                captureTile(tile);
                // end turn
                break;
            case AVAILABLE_TILE:
                moveToTile(tile);
                // end turn
                break;
            case SPECIAL_MOVE:
                specialMove(tile);
                // end turn
                break;
            default:
                dropPiece();
                break;
        }
        // Reset important tiles and repaint the board after the move
        dropPiece();
        gameScreenFactory.resetColors(importantTiles);
    }

    /** Checks last time if time is empty.
     * Removes piece.homeTile.piece reference to null.
     * Refreshes chess icon. Removes tile.piece from tile.piece.homeTile observers
     * tile.setPiece(selectedPiece)
     * Refresh all observers
     * Set verified icon on that tile */
    private void moveToTile(Tile tile) {
        if(tile.getPiece() != null){ captureTile(tile); }// safely remove the piece from tile.
        else{

            Tile homeTile = takeSelectedPieceFromItsHomeTile();
            // set piece to new tile and set home tile
            tile.setPiece(selectedPiece);
            selectedPiece.lookAround();
            tile.refreshChessPieceIcon();
            updateObserversAfterAMove(homeTile);
        }
    }

    /** remove a specific tile from piece references, observers and other tiles.
     * Refresh icon updates. 
     * @return tileOfOrigin*/
    private Tile takeSelectedPieceFromItsHomeTile() {
        Tile homeTile = selectedPiece.getHomeTile();
        homeTile.setPiece(null);
        homeTile.refreshChessPieceIcon();
        selectedPiece.stopObservingImportantTiles();
        return homeTile;
    }

    private void specialMove(Tile tile) {
        if(selectedPiece.getClass()== King.class){
            // get ammount of moves made by King.
            // if == 0.
            // check if rook has 0
            // 3 variants of castling.
            // 2 tiles "up"
            // 2 tiles "left"
            // 2 tiles "right"
        } else if(selectedPiece.getClass() == Pawn.class){
            // if row is the "last" row
            // promote
            // en passant is attack tile
        }
    }

    private void captureTile(Tile tile) {
        if(tile.getPiece() != null && tile.getPiece().getColor() != selectedPiece.getColor()){
            Tile tileOfOrigin = takeSelectedPieceFromItsHomeTile();
            ChessPiece capturedPiece = chessboard.removePieceFromMaterial(tile.getPiece());
            capturedPiece.stopObservingImportantTiles();
            gameInstance.getPlayerObject(capturedPiece.getColor()).addToGraveyard(capturedPiece);
            updateObserversAfterAMove(tileOfOrigin);
        }else{ throw IllegalArgumentException.class.cast("Trying to capture empty or an allied tile"); }
    }

    private void updateObserversAfterAMove(Tile homeTile) {
        for (ChessPiece observer : homeTile.getListOfObservers()) {
            observer.lookAround();
        }
    }

    private void dropPiece() {
        gameScreenFactory.resetColors(importantTiles);

        selectedPiece = null;
        importantTiles = null;
    }
    
    private SpecTileFunc checkSpecTileFunc(Tile tile) {
        for (Map.Entry<SpecTileFunc, List<Tile>> entry : importantTiles.entrySet()) {
            List<Tile> tiles = entry.getValue(); // Extract the Tile[] array for the key
            if (tiles != null) {
                for (Tile t : tiles) { // Iterate through each Tile in the array
                    if (t.equals(tile)) { // Check if the current Tile matches the given Tile
                        return entry.getKey(); // Return the corresponding key
                    }
                }
            }
        }
        return null; // Return null if no match is found
    }

    /**
     * Creates a new game instance and sets up the chessboard, players, and visuals.
     *
     * @param white The Player object for the white pieces.
     * @param black The Player object for the black pieces.
     */
    private void CreateGame(Player white, Player black) {
        gameInstance = new GameInstance(white, black, GameScreenFactory.getTileSize());
        gameScreenFactory = new GameScreenFactory(gameInstance, mainWindowFrame);

        // Retrieve the chessboard instance from the game instance
        chessboard = gameInstance.getChessboard();
    }

    private void colorImportantTilesFor(ChessPiece chessPiece){
        importantTiles = chessPiece.getImportantTiles();
        for (Map.Entry<SpecTileFunc, List<Tile>> entry : importantTiles.entrySet()) {
            SpecTileFunc color = entry.getKey();
            List<Tile> tiles = entry.getValue();
            for (Tile tile : tiles) {
                tile.paintButton(color);
            }
        }
    }


}