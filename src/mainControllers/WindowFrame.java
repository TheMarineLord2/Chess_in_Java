package mainControllers;

import javax.swing.*;

public class WindowFrame extends JFrame {
    // private List<JPanel> panelBuffor;
    private static WindowFrame instance;
// Set a GridLayout with one row and one column to ensure 
// that any added JPanel will always expand to fill the window, 
// regardless of the JFrame's size or content changes.

    //Starting settings
    private WindowFrame() {
        super("Chess");
        setSize(800, 800);
        setLayout(new java.awt.GridLayout(1, 1));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    //Singleton
    public static WindowFrame getInstance(){
        if (instance == null) {
            // Tworzy instancję, jeśli taka jeszcze nie istnieje
            instance = new WindowFrame();
        }
        return instance;
    }
}
