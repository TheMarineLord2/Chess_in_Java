package mainControllers.mainScreen;

import javax.swing.*;

public class MainWindowFrame extends JFrame {
    // private List<JPanel> panelBuffor;
    private static MainWindowFrame instance;
// Set a GridLayout with one row and one column to ensure 
// that any added JPanel will always expand to fill the window, 
// regardless of the JFrame's size or content changes.

    //Starting settings
    private MainWindowFrame() {
        super("Chess");
        setSize(1200, 1000);
        setLayout(new java.awt.GridLayout(1, 1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
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
