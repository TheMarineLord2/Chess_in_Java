package org.example.mainControllers.mainScreen;

import org.example.mainControllers.gameControlls.GameOperator;
import org.example.mainControllers.gameControlls.Player;

import javax.swing.*;

public class MainOperator
{
    private static MainOperator instance;
    private final MainWindowFrame windowFrame;
    private final TitleScreenFactory titleScreenFactory = TitleScreenFactory.getInstance();
    private Player player1;
    private Player player2;

    private MainOperator(){
        windowFrame = MainWindowFrame.getInstance();
        initializeMainWindow();
    }

    public static MainOperator getInstance(){
        if ( instance == null)
        {
            instance = new MainOperator();
        }
        return instance;
    }

    private void initializeMainWindow() {
        windowFrame.add(titleScreenFactory);
        windowFrame.setVisible(true);
    }


    public void startNewGameButtonPressed() {
        if (titleScreenFactory != null)
        {
            String player1Name = titleScreenFactory.getPlayer1Name();
            String player2Name = titleScreenFactory.getPlayer2Name();

            if (player1Name != null && !player1Name.isEmpty() && player2Name != null && !player2Name.isEmpty())
            {
                player1 = new Player(player1Name);
                player2 = new Player(player2Name);

                System.out.println("Creating new game instance...");
                System.out.println("White player: " + player1.getName());
                System.out.println("Black player: " + player2.getName());

                Thread gameThread = new Thread (new GameOperator(this, player1, player2));
                gameThread.start();
            }
            else
            {
                JOptionPane.showMessageDialog(windowFrame,"Both player names must be filled in!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public Player getPlayer1()
    {
        return player1;
    }
    public Player getPlayer2()
    {
        return player2;
    }
    public MainWindowFrame getMainWindowFrame()
    {
        return windowFrame;
    }
}
