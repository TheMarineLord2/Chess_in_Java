package mainControllers.gameControlls;

import chessboardElements.chessboard.Chessboard;

// GameInstance sprawuje bezpośredni nadzór nad
// powołaniem gry, przypisaniem graczy, przebiegu
// tur oraz nad innymi kontrollerami.
public class GameInstance {

    // vartości kontrolowane przez instancje
    private Chessboard chessboard;
    private int turn_counter;
    private Player white;
    private Player black;

    // zablokowanie domyślnego konstruktora
    private GameInstance(){}
    // konstruktor właściwy
    public GameInstance(Player white, Player black){
        this.white = white;
        this.black = black;
        this.chessboard = new Chessboard();
        this.turn_counter = 0;
    }

    public Player getPlayer(int playerNumber){
        if(playerNumber == 1){
            return white;
        }else if(playerNumber == 2){
            return black;
        }else{
            return null;
        }
    }
    public Chessboard getChessboard(){
        return chessboard;
    }
    public int getTurnCounter(){
        return turn_counter;
    }
}
