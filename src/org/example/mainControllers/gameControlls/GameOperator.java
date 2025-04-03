package org.example.mainControllers.gameControlls;

import org.example.mainControllers.mainScreen.MainOperator;
import org.example.mainControllers.mainScreen.MainWindowFrame;

import javax.swing.*;

public class GameOperator {
    private GameScreenFactory gameScreenFactory;
    private GameInstance gameInstance;
    private MainWindowFrame mainWindowFrame;
    private MainOperator mainOperator;

    public GameOperator(JFrame mainWindowFrame, Player white, Player black){
        mainOperator = MainOperator.getInstance();
        this.mainWindowFrame = (MainWindowFrame) mainWindowFrame;
        CreateGame(white, black);

    }

    private void CreateGame(Player white, Player black){
        gameInstance = new GameInstance(white, black, GameScreenFactory.getTileSize());
        gameScreenFactory = GameScreenFactory.getInstance(mainWindowFrame, gameInstance);
    }
}
