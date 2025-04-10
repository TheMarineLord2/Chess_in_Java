package org.example.mainControllers.gameControlls;

import com.sun.source.tree.ImportTree;
import org.example.chessboardElements.SpecialTileColors;
import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.chessboard.Tile;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.mainControllers.mainScreen.MainOperator;
import org.example.mainControllers.mainScreen.MainWindowFrame;

import java.util.Map;

/**
 * The GameOperator class handles the main logic and control flow of the chess game.
 * This includes managing the game state, piece selection, and user interactions with the chessboard.
 */
public class GameOperator {

    // Singleton instance of the GameOperator class
    private static GameOperator instance;

    // Factory for creating and controlling game screen visuals
    private GameScreenFactory gameScreenFactory;

    // Current instance containing the chess game's state
    private GameInstance gameInstance;

    // Reference to the main application window frame
    private MainWindowFrame mainWindowFrame;

    // Main operator managing the entire application logic
    private MainOperator mainOperator;

    // Field to track the currently selected chess piece
    private ChessPiece selectedPiece = null;

    // Reference to the game's chessboard object
    private Chessboard chessboard = null;

    // Map of important tiles based on special moves or interactions
    private Map<SpecialTileColors, Tile[]> importantTiles = null;

    /**
     * Private constructor for the singleton design pattern.
     * Initializes the game setup by retrieving references
     * from MainOperator and creating a new game instance.
     */
    private GameOperator() {
        mainOperator = MainOperator.getInstance();
        this.mainWindowFrame = mainOperator.getMainWindowFrame();
        CreateGame(mainOperator.getPlayer1(), mainOperator.getPlayer2());
    }

    /**
     * Retrieves the single instance of the GameOperator class.
     * Ensures only one instance exists during the application lifecycle.
     *
     * @return The singleton instance of GameOperator.
     */
    public static GameOperator getInstance() {
        if (instance == null) {
            instance = new GameOperator();
        }
        return instance;
    }

    public GameInstance getGameInstance(){
        return gameInstance;
    }
    public MainWindowFrame getMainWindowFrame(){
        return mainWindowFrame;
    }

    /**
     * Handles the logic for when a tile on the chessboard is clicked.
     * If no piece is selected, it selects the piece on the clicked tile.
     * If a piece is already selected, it attempts to move the piece based on
     * valid moves and special interactions.
     *
     * @param row    The row index of the clicked tile.
     * @param column The column index of the clicked tile.
     */
    public void clickedTile(int row, int column) {
        try {
            if (selectedPiece == null) {
                // Grab the piece on the clicked tile
                selectedPiece = chessboard.getTile(row, column).getPiece();

                // Set the piece visuals as the cursor representation
                gameScreenFactory.setImageAsCursor(selectedPiece.getChessPieceVisuals());

                // Show possible moves for the selected piece
                importantTiles = selectedPiece.getImportantTiles();
                gameScreenFactory.repaint(importantTiles);
            } else {
                // Iterate over important tiles to check if the selected move is valid
                for (Map.Entry<SpecialTileColors, Tile[]> entry : importantTiles.entrySet()) {
                    SpecialTileColors color = entry.getKey();
                    Tile[] tiles = entry.getValue();

                    for (Tile tile : tiles) {
                        if (tile.getRow() == row && tile.getColumn() == column) {
                            // Perform the move according to tile color and game rules

                            // Update observers for the previous and new tile
                            // Drop piece. Reset it's own observed tiles and recolor them.
                            dropPiece();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log errors during click handling
        }
    }

    private void dropPiece() {
        gameScreenFactory.resetColors(importantTiles);
        
        selectedPiece = null;
        importantTiles = null;
    }

    public void rightMouseClicked(){
        //gameScreenFactory
    }
    //private methods

    /**
     * Creates a new game instance and sets up the chessboard, players, and visuals.
     *
     * @param white The Player object for the white pieces.
     * @param black The Player object for the black pieces.
     */
    private void CreateGame(Player white, Player black) {
        gameInstance = new GameInstance(white, black, GameScreenFactory.getTileSize());
        gameScreenFactory = GameScreenFactory.getInstance();

        // Retrieve the chessboard instance from the game instance
        chessboard = gameInstance.getChessboard();
    }

    private void colorImportantTilesFor(ChessPiece chessPiece){
        importantTiles = chessPiece.getImportantTiles();
        for (Map.Entry<SpecialTileColors, Tile[]> entry : importantTiles.entrySet()) {
            SpecialTileColors color = entry.getKey();
            Tile[] tiles = entry.getValue();
            for (Tile tile : tiles) {
                tile.paintButton(color);
            }
        }
    }
}