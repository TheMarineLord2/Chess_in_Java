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
        gamesWon = 0;
        gamesLost = 0;
        gamesDrawn = 0;
    }
    public String getName(){
        return playerName;
    }
}
