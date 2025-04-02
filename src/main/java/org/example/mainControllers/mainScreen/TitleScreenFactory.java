package mainControllers.mainScreen;

//import mainControllers.gameControlls.GameInstance;
//import mainControllers.gameControlls.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TitleScreenFactory extends JPanel {
    // Referencje zewnętrzntch singletonwow
    private static MainOperator mainOperator = MainOperator.getInstance();
    // Singleton instance of TitleScreenFactory
    private static TitleScreenFactory instance;

    // ELEMENTY PANELU
    // Referencje do pól 
    private final JTextField    player1NameTextbox = player1NameTextbox();
    private final JTextField    player2NameTextbox = player2NameTextbox();
    private final JButton       startNewGameButton = startNewGameButton();
    // wartosci ulozenia wszystkich pol
    private final int   startNewGameButtonX = 300,  startNewGameButtonWidth = 200,
                        startNewGameButtonY = 100,   startNewGameButtonHeight = 80;
    private final int   player1TextboxX = 200,      player1TextboxWidth = 200,
                        player1TextboxY = 250,      player1TextboxHeight = 80;
    private final int   player2TextboxX = 400,      player2TextboxWidth = 200,
                        player2TextboxY = 250,      player2TextboxHeight = 80;

     
    // Prywatny konstruktor singletona
    // Layout null i przywołanie każdego wymaganego elementu
    private TitleScreenFactory() {
        // ustawiamy układ elementow
        setLayout(null);
        setBackground(Color.BLUE);

        add(startNewGameButton);
        add(player1NameTextbox);
        add(player2NameTextbox);
    }
    // Singleton instance getter
    public static TitleScreenFactory getInstance() {
        if (instance == null) {
            instance = new TitleScreenFactory();
        }
        return instance;
    }
    // metody publiczne
    public String getPlayer1Name() {
        return player1NameTextbox.getText();
    }
    public String getPlayer2Name() {
        return player2NameTextbox.getText();
    }
    // metody prywatne
    // create button, add listener, call methods
    private JButton startNewGameButton(){
        JButton button = new JButton("Start New Game");
        button.setBounds(startNewGameButtonX, startNewGameButtonY, startNewGameButtonWidth, startNewGameButtonHeight);

        // zdefiniuj zachowania dla najechania myszką
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainOperator.startNewGameButtonPressed();
            }
        });
        return button;
    }
    private JTextField player1NameTextbox(){
        JTextField textbox = new JTextField();
        textbox.setBounds(player1TextboxX, player1TextboxY, player1TextboxWidth, player1TextboxHeight);
        return textbox;
    }

    private JTextField player2NameTextbox() {
        JTextField textbox = new JTextField();
        textbox.setBounds(player2TextboxX, player2TextboxY, player2TextboxWidth, player2TextboxHeight);
        return textbox;
    }

}
