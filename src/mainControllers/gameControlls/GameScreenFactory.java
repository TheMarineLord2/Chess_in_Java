package mainControllers.gameControlls;

import chessboardElements.chessboard.Chessboard;
import chessboardElements.chessboard.Tile;
import mainControllers.mainScreen.MainWindowFrame;

import javax.swing.*;
import java.awt.*;

public class GameScreenFactory {
    private Chessboard chessboard;
    private Player white;
    private Player black;
    private MainWindowFrame mainWindowFrame;

    public GameScreenFactory(MainWindowFrame mainWindowFrame, GameInstance gameInstance) {
        // Assign most important fields and refferences.
        this.chessboard = gameInstance.getChessboard();
        this.white = gameInstance.getPlayer(1);
        this.black = gameInstance.getPlayer(2);
        this.mainWindowFrame = mainWindowFrame;
        // Clear Frame
        mainWindowFrame.getContentPane().removeAll();

        // Build Panel base;
        CreateMainPanelBase();
    }

    private void CreateMainPanelBase(){
        // Create a main panel occupying 1200x1000 area
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(1200, 1000));
        mainPanel.setLayout(new BorderLayout());

        // Create an 800x800 chessboard panel
        JPanel chessboardPanel = new JPanel(new GridLayout(8, 8));
        chessboardPanel.setPreferredSize(new Dimension(800, 800));

        // Create player profile labels
        JLabel player1Profile = new JLabel(white.getName(), SwingConstants.CENTER);
        player1Profile.setPreferredSize(new Dimension(200, 1000));
        JLabel player2Profile = new JLabel(black.getName(), SwingConstants.CENTER);
        player2Profile.setPreferredSize(new Dimension(200, 1000));


        // Add components to the main panel
        mainPanel.add(player1Profile, BorderLayout.WEST);
        mainPanel.add(chessboardPanel, BorderLayout.CENTER);
        mainPanel.add(player2Profile, BorderLayout.EAST);
        Tile[][] tiles = chessboard.getPlayingField();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessboardPanel.add(tiles[i][j].getFieldButton());
            }
        }
        // Add the main panel to the main window frame
        mainWindowFrame.add(mainPanel);
        mainWindowFrame.revalidate();
    }
}
