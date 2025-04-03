package org.example.mainControllers.gameControlls;

import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.chessboard.Tile;
import org.example.mainControllers.mainScreen.MainWindowFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;


// important things done in this class:
// set up visual representation of player pannels
// set up visual representation of chessboard
// Listener => If Chessboard Tile is clocked
// Listener => If Mouse OutOfBounds && holding a chessPiece.
//

public class GameScreenFactory {
    // Singleton instance
    private static GameScreenFactory instance;
    // variables that really matter
    private static final int MAIN_PANEL_WIDTH = 1200;
    private static final int MAIN_PANEL_HEIGHT = 820;
    private static final int TILE_SIZE = 100;
    private static final int OUT_OF_BOUNDS_MARIGIN_OF_ERROR = 10;


    // derivative variables
    private static final int CHESSBOARD_PANEL_SIZE = 8 * TILE_SIZE;
    private static final int CHESSBOARD_PANEL_X = (MAIN_PANEL_WIDTH - CHESSBOARD_PANEL_SIZE)/2;
    private static final int CHESSBOARD_PANEL_Y = (MAIN_PANEL_HEIGHT - CHESSBOARD_PANEL_SIZE)/2;
    private static final int PLAYER_PANEL_WIDTH = CHESSBOARD_PANEL_X;
    private static final int PLAYER_PANEL_HEIGHT = MAIN_PANEL_HEIGHT;
    //              // derivative but for OutOfBoundsListener
    private static final int OUT_OF_BOUNDS_X = CHESSBOARD_PANEL_X - OUT_OF_BOUNDS_MARIGIN_OF_ERROR;
    private static final int OUT_OF_BOUNDS_Y = CHESSBOARD_PANEL_Y - OUT_OF_BOUNDS_MARIGIN_OF_ERROR;
    private static final int OUT_OF_BOUNDS_WIDTH = CHESSBOARD_PANEL_SIZE + 2 * OUT_OF_BOUNDS_MARIGIN_OF_ERROR;
    private static final int OUT_OF_BOUNDS_HEIGHT = CHESSBOARD_PANEL_SIZE + 2 * OUT_OF_BOUNDS_MARIGIN_OF_ERROR;

    // static reference to important objects.
    private Chessboard chessboard;
    private Player white;
    private Player black;
    private MainWindowFrame mainWindowFrame;
    private MouseMotionAdapter outOfBoundsListener;

    // static reference to basic WindowPanels
    private JLabel player1Panel;
    private JLabel player2Panel;
    private JPanel chessboardPanel;

    private GameScreenFactory(MainWindowFrame mainWindowFrame, GameInstance gameInstance) {
        // Assign most important fields and references.
        this.chessboard = gameInstance.getChessboard();
        this.white = gameInstance.getPlayer(1);
        this.black = gameInstance.getPlayer(2);
        this.mainWindowFrame = mainWindowFrame;
        setUpOutOfBoundsListener();
        // Clear Frame
        mainWindowFrame.getContentPane().removeAll();
        // Build Panel base;
        CreateMainPanelBase();
    }

    // Singleton getter method
    public static GameScreenFactory getInstance(MainWindowFrame mainWindowFrame, GameInstance gameInstance) {
        if (instance == null) {
            instance = new GameScreenFactory(mainWindowFrame, gameInstance);
        }
        return instance;
    }
    public static GameScreenFactory getInstance() { return instance;}

    private void CreateMainPanelBase(){
        // Create a main panel occupying MAIN_PANEL_WIDTH x MAIN_PANEL_HEIGHT area
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0,0,MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);
        mainPanel.setLayout(null);

        // Create a chessboard panel occupying CHESSBOARD_PANEL_WIDTH x CHESSBOARD_PANEL_HEIGHT
        chessboardPanel = new JPanel(null);
        chessboardPanel.setBounds(CHESSBOARD_PANEL_X, CHESSBOARD_PANEL_Y, CHESSBOARD_PANEL_SIZE, CHESSBOARD_PANEL_SIZE);

        // Create player profile labels
        player1Panel = new JLabel(white.getName());
        player1Panel.setBounds(0, 0, PLAYER_PANEL_WIDTH, PLAYER_PANEL_HEIGHT);
        player2Panel = new JLabel(black.getName());
        player2Panel.setBounds(MAIN_PANEL_WIDTH - PLAYER_PANEL_WIDTH, 0, PLAYER_PANEL_WIDTH, PLAYER_PANEL_HEIGHT);

        // Create main structure
        mainPanel.add(player1Panel);
        mainPanel.add(chessboardPanel);
        mainPanel.add(player2Panel);


        // Finish the playing field
        setUpChessboardPanel();
        // Add the main panel to the main window frame
        mainWindowFrame.add(mainPanel);
        mainWindowFrame.revalidate();
    }

    // publiczne metody
    public static int getTileSize(){
        return TILE_SIZE;
    }
    public void setImageAsCursor(BufferedImage image){
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Cursor cursor = toolkit.createCustomCursor(image, new Point(10,10),"cursor");
        mainWindowFrame.setCursor(cursor);
        mainWindowFrame.revalidate();
        System.out.println("Cursor set");
    }
    // prywatne metody
    private void setUpChessboardPanel(){
        setChessboardTiles();
        showChessPieces();
    }
    private void setChessboardTiles(){
        Tile[][] tiles = chessboard.getPlayingField();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessboardPanel.add(tiles[i][j].getButton());
            }
        }
    }
    private void showChessPieces() {
        Tile[][] tiles = chessboard.getPlayingField();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tiles[i][j].getPiece() != null) {
                    tiles[i][j].showChessPiece();
                }
            }
        }
    }
    private void setUpOutOfBoundsListener(){
        outOfBoundsListener = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                if(evt.getX() < OUT_OF_BOUNDS_X || evt.getX() > OUT_OF_BOUNDS_X + OUT_OF_BOUNDS_WIDTH ||
                        evt.getY() < OUT_OF_BOUNDS_Y || evt.getY() > OUT_OF_BOUNDS_Y + OUT_OF_BOUNDS_HEIGHT ){
                    // DROP THE CHESS PIECE
                    //
                    // REMOVE THE LISTENER
                    chessboardPanel.removeMouseMotionListener(outOfBoundsListener);
                    // CHANGE MOUSE CURSOR TO DEAFULT
                }
            }
        };
    }
}
