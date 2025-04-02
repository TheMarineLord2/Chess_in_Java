package mainControllers.mainScreen;

import mainControllers.gameControlls.GameOperator;
import mainControllers.gameControlls.Player;
import mainControllers.gameControlls.GameOperator;
import javax.swing.*;


import mainControllers.gameControlls.GameInstance;

// Main Operator jest pierwszym wywoływanym operatorem
// WYMIENIA się odpowiedzialnością z Game Operator

//
public class MainOperator {
    // holds reference to instance. Starts as null.
    private static MainOperator instance;
    private JFrame windowFrame = MainWindowFrame.getInstance();
    private TitleScreenFactory titleScreenFactory = TitleScreenFactory.getInstance();
    private GameOperator gameOperator;
    private Player player1;
    private Player player2;

    // KONSTRUKTORY
    // Initializes the main application window
    private MainOperator(){
        initializeMainWindow();
    }
    // public methods
    // create SINGLETON instance
    public static MainOperator getInstance(){
        if(instance==null){
            instance = new MainOperator();
        }
        return instance;
    }
    // private methods
    private void initializeMainWindow() {
        windowFrame.add(titleScreenFactory);
        windowFrame.setVisible(true);
    }

    // check textfields. If nof filled, show error.
    public void startNewGameButtonPressed() {
        if (titleScreenFactory != null) {
            String player1Name = titleScreenFactory.getPlayer1Name();
            String player2Name = titleScreenFactory.getPlayer2Name();

            if (player1Name != null && !player1Name.isEmpty() &&
                    player2Name != null && !player2Name.isEmpty()) {
                gameOperator = new GameOperator(windowFrame,new Player(player1Name), new Player(player2Name));
            } else {
                // Instead we should change title screan parameters
                JOptionPane.showMessageDialog(windowFrame,
                        "Both player names must be filled in!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
