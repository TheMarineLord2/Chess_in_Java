package mainControllers.gameControlls;

public class Player {
    String playerName;
    int gamesWon;
    int gamesLost;
    int gamesDrawn;

    //zablokowanie pustego konstruktora;
    private Player(){}
    //domyślny konstruktor
    public Player(String name){
        playerName = name;
    }
    public String getName(){
        return playerName;
    }
}
