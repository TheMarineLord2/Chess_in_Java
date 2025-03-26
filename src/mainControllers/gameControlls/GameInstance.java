package mainControllers.gameControlls;

import chessboardElements.chessboard.Chessboard;

// GameInstance sprawuje bezpośredni nadzór nad
// powołaniem gry, przypisaniem graczy, przebiegu
// tur oraz nad innymi kontrollerami.
public class GameInstance {

    // vartości kontrolowane przez instancje
    Chessboard chessboard;
    int turn_counter;
    Player white;
    Player black;

    // zablokowanie domyślnego konstruktora
    private GameInstance(){}
    // konstruktor właściwy
    public GameInstance(Player white, Player black){
        this.white = white;
        this.black = black;
        this.chessboard = new Chessboard();
        this.turn_counter = 0;
    }
}
