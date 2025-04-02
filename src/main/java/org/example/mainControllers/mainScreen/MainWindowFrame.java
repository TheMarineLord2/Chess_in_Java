package mainControllers.mainScreen;

import javax.swing.*;

public class MainWindowFrame extends JFrame {
    // private List<JPanel> panelBuffor;
    private static MainWindowFrame instance;
    
    // Set a GridLayout with one row and one column to ensure 
    // that any added JPanel will always expand to fill the window, 
    // regardless of the JFrame's size or content changes.

    // Constants for dimension management
    private static final int MAIN_FRAME_WIDTH = 1200;
    private static final int MAIN_FRAME_HEIGHT = 920;

    //Starting settings
    private MainWindowFrame() {
        super("Chess");
        setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
        setLayout(new java.awt.GridLayout(1, 1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //Singleton
    public static MainWindowFrame getInstance(){
        if (instance == null) {
            // Tworzy instancję, jeśli taka jeszcze nie istnieje
            instance = new MainWindowFrame();
        }
        return instance;
    }
}
