package org.example.mainControllers.gameControlls;

import org.example.objectsAndElements.pieces.ChessPiece;

import java.util.ArrayList;

public class Player {
    String playerName;
    int gamesWon;
    int gamesLost;
    int gamesDrawn;
    private ArrayList<ChessPiece> graveyard = new ArrayList<>();

    //zablokowanie pustego konstruktora;
    private Player(){}

    //domyślny konstruktor
    public Player(String name) {
        playerName = name;
        graveyard = new ArrayList<>();
    }

    public String getName(){
        return playerName;
    }

    // Adds a chess piece to the graveyard
    public void addToGraveyard(ChessPiece chessPiece) {
        graveyard.add(chessPiece);
    }

    // Returns the list of pieces in the graveyard
    public ArrayList<ChessPiece> getGraveyard() {
        return graveyard;
    }
}
