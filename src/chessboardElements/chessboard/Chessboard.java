package chessboardElements.chessboard;

public class Chessboard {
    private Tile[][] playingField;

    // podczas wywołania wypełnia obiekty klasy
    // po zbudowaniu planszy wymaga ustawienia figur
    public Chessboard(){
        buildChessboard();
    }
    // metody publiczne
    // zwraca plansze 8x8
    public Tile[][] getPlayingField() {
        return playingField;
    }
    // metody prywatne
    private void buildChessboard(){
        playingField = new Tile[8][8];
        for (int i = 0; i < 8; i++) { // Rows
            for (int j = 0; j < 8; j++) { // Columns
                if ((i + j) % 2 == 0) {
                    // Even sum of indexes: BLACK
                    playingField[i][j] = new Tile(TileColor.BLACK);
                } else {
                    // Odd sum of indexes: WHITE
                    playingField[i][j] = new Tile(TileColor.WHITE);
                }
            }
        }
    }

    private void setBlackPawns(){
        for(int i = 0; i < 8; i++){
            //playingField[6][i].setPiece(new Pawn());
        }
    }
    private void setWhitePawns(){}
    private void setBlackRooks(){}
    private void setWhiteRooks(){}
    private void setBlackKnights(){}
    private void setWhiteKnights(){}
    private void setBlackBishops(){}
    private void setWhiteBishops(){}
    private void setBlackQueens(){}
    private void setWhiteQueens(){}
    private void setBlackKing(){}
    private void setWhiteKing(){}
    private void setBlackPieces(){}
    private void setWhitePieces(){}
}
