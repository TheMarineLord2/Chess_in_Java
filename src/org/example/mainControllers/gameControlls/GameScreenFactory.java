package org.example.mainControllers.gameControlls;

import org.example.chessboardElements.GameOperator;
import org.example.chessboardElements.SpecialTileColors;
import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.chessboard.Tile;
import org.example.mainControllers.mainScreen.MainWindowFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.Map;


// This class is responsible for the graphical setup and control of the main game screen.
// Key functionalities:
// 1. Setting up visual representations for the player panels.
// 2. Setting up the visual representation for the chessboard.
// 3. Implementing listeners for chessboard tile clicks and detecting when the mouse moves out of bounds.

public class GameScreenFactory {
    // NOTE: Garbage collection is intentionally not handled manually in this class.

    // Singleton instance of GameScreenFactory to ensure only one instance exists at runtime.
    private static GameScreenFactory instance;

    // Core variables for managing panel dimensions and chessboard settings.
    private static final int MAIN_PANEL_WIDTH = 1200;
    private static final int MAIN_PANEL_HEIGHT = 820;
    private static final int TILE_SIZE = 100;
    private static final int OUT_OF_BOUNDS_MARIGIN_OF_ERROR = 10;
    // Derived variables used for positioning and sizing the chessboard and player panels.
    private static final int CHESSBOARD_PANEL_SIZE = 8 * TILE_SIZE;
    private static final int CHESSBOARD_PANEL_X = (MAIN_PANEL_WIDTH - CHESSBOARD_PANEL_SIZE)/2;
    private static final int CHESSBOARD_PANEL_Y = (MAIN_PANEL_HEIGHT - CHESSBOARD_PANEL_SIZE)/2;
    private static final int PLAYER_PANEL_WIDTH = CHESSBOARD_PANEL_X;
    private static final int PLAYER_PANEL_HEIGHT = MAIN_PANEL_HEIGHT;
    // Variables used for setting up boundaries and margins for the out-of-bounds mouse listener.
    private static final int OUT_OF_BOUNDS_X = CHESSBOARD_PANEL_X - OUT_OF_BOUNDS_MARIGIN_OF_ERROR;
    private static final int OUT_OF_BOUNDS_Y = CHESSBOARD_PANEL_Y - OUT_OF_BOUNDS_MARIGIN_OF_ERROR;
    private static final int OUT_OF_BOUNDS_WIDTH = CHESSBOARD_PANEL_SIZE + 2 * OUT_OF_BOUNDS_MARIGIN_OF_ERROR;
    private static final int OUT_OF_BOUNDS_HEIGHT = CHESSBOARD_PANEL_SIZE + 2 * OUT_OF_BOUNDS_MARIGIN_OF_ERROR;

    // Static references to key game objects such as players, chessboard, and game operator.
    private final Chessboard chessboard;
    private final Player white;
    private final Player black;
    private final MainWindowFrame mainWindowFrame;
    private MouseMotionAdapter outOfBoundsListener;
    private final GameOperator gameOperator;

    // static reference to basic WindowPanels
    private JLabel player1Panel;
    private JLabel player2Panel;
    private JPanel chessboardPanel;
    private JPanel mainPanel;

    private GameScreenFactory() {
        // Initialize core fields by retrieving the current game state and necessary objects.
        this.gameOperator = GameOperator.getInstance();
        this.chessboard = gameOperator.getGameInstance().getChessboard();
        this.white = gameOperator.getGameInstance().getPlayer(1);
        this.black = gameOperator.getGameInstance().getPlayer(2);
        this.mainWindowFrame = gameOperator.getMainWindowFrame();
        // setUpOutOfBoundsListener();

        // Clear the contents of the main game window before reconfiguring its layout.
        mainWindowFrame.getContentPane().removeAll();
        // Initialize the main panel structure and associated components.
        createMainPanelStructure();
    }

    /**
     * Retrieves the singleton instance of GameScreenFactory.
     * If it doesn't exist, creates a new instance.
     *
     * @return the singleton GameScreenFactory instance.
     */
    public static GameScreenFactory getInstance() {
        if (instance == null) {
            instance = new GameScreenFactory();
        }
        return instance;
    }

    /**
     * Sets up the main panel structure by creating the main panel,
     * chessboard panel, and player panels.
     */
    private void createMainPanelStructure() {
        createMainPanel();
        createChessboardPanel();
        createPlayerPanels();
        // Adds the main panel to the main window frame and revalidates it.

        mainWindowFrame.revalidate();
    }

    /**
     * Sets up the main panel with specified dimensions and layout.
     */
    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBounds(0,0,MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);
        mainPanel.setLayout(null);
        
        mainWindowFrame.add(mainPanel);
    }

    /**
     * Creates the chessboard panel and configures its size and layout.
     * Populates the panel with chessboard tiles and pieces.
     */
    private void createChessboardPanel() {
        // Initialize the chessboard panel with specified dimensions.
        chessboardPanel = new JPanel(null);
        chessboardPanel.setBounds(CHESSBOARD_PANEL_X, CHESSBOARD_PANEL_Y, CHESSBOARD_PANEL_SIZE, CHESSBOARD_PANEL_SIZE);
        setUpChessboardPanel();
        
        mainPanel.add(chessboardPanel);
    }

    /**
     * Creates and configures the player panels for both players, displaying their names.
     */
    private void createPlayerPanels() {
        // Create player profile labels
        player1Panel = new JLabel(white.getName());
        player1Panel.setBounds(0, 0, PLAYER_PANEL_WIDTH, PLAYER_PANEL_HEIGHT);
        player2Panel = new JLabel(black.getName());
        player2Panel.setBounds(MAIN_PANEL_WIDTH - PLAYER_PANEL_WIDTH, 0, PLAYER_PANEL_WIDTH, PLAYER_PANEL_HEIGHT);

        mainPanel.add(player1Panel);
        mainPanel.add(player2Panel);
    }

    // Public methods to interact with the game screen.
    public static int getTileSize(){
        return TILE_SIZE;
    }

    /**
     * Updates the mouse cursor to display a custom image.
     *
     * @param image BufferedImage to use as the cursor.
     */
    public void setImageAsCursor(BufferedImage image) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Cursor cursor = toolkit.createCustomCursor(image, new Point(10,10),"cursor");
        mainWindowFrame.setCursor(cursor);
        mainWindowFrame.revalidate();
        System.out.println("Cursor set successfully.");
    }
    // Private helper methods for initializing and updating components.

    /**
     * Configures the chessboard panel by adding tiles and displaying chess pieces.
     */
    private void setUpChessboardPanel() {
        setChessboardTiles(); // Adds tile buttons to the chessboard.
        showChessPieces(); // Displays chess pieces on their respective tiles.
    }

    /**
     * Adds all tiles from the chessboard model to the chessboard panel.
     */
    private void setChessboardTiles() {
        Tile[][] tiles = chessboard.getPlayingField();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessboardPanel.add(tiles[i][j].getButton());
            }
        }
    }

    /**
     * Loops through all the chessboard tiles and displays their corresponding chess pieces.
     */
    private void showChessPieces() {
        Tile[][] tiles = chessboard.getPlayingField();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tiles[i][j].getPiece() != null) {
                    tiles[i][j].refreshChessPieceIcon();
                }
            }
        }
    }

    private void setUpPieceInHandListeners(){
        outOfBoundsListener = new MouseMotionAdapter() {
            /**
             * Detects if the mouse pointer goes out of bounds while holding a chess piece
             * and resets the cursor and active listeners accordingly.
             */
            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                if(evt.getX() < OUT_OF_BOUNDS_X || evt.getX() > OUT_OF_BOUNDS_X + OUT_OF_BOUNDS_WIDTH ||
                        evt.getY() < OUT_OF_BOUNDS_Y || evt.getY() > OUT_OF_BOUNDS_Y + OUT_OF_BOUNDS_HEIGHT ){


                    // Drops the chess piece, removes the out-of-bounds listener,
                    // and resets the mouse cursor to the default state.
                    chessboardPanel.removeMouseMotionListener(outOfBoundsListener);
                    // CHANGE MOUSE CURSOR TO DEAFULT
                }
            }
        };
    }

    /**
     * Resets all tile colors on the chessboard to their default state.
     */
    public void resetChessboardColors() {
        chessboard.resetButtonColors();
        mainWindowFrame.repaint();
    }

    public void paintSpecialTiles(Map<SpecialTileColors, Tile[]> importantTiles) {
        for (Map.Entry<SpecialTileColors, Tile[]> entry : importantTiles.entrySet()) {
            for (Tile tile : entry.getValue()) {
                tile.paintButton(entry.getKey());
            }
        }
        mainWindowFrame.repaint();
    }
    public void resetColors(Map<SpecialTileColors, Tile[]> importantTiles){
        for (Map.Entry<SpecialTileColors, Tile[]> entry : importantTiles.entrySet()) {
            for (Tile tile : entry.getValue()) {
                tile.resetButtonColor();
            }
        }
        mainWindowFrame.repaint();
    }
}
