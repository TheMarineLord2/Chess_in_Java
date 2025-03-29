package chessboardElements.chessboard;

import chessboardElements.pieces.pieceType.*;

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
    private void buildChessboard() {
        playingField = new Tile[8][8];
        for (int i = 0; i < 8; i++) { // Rows
            for (int j = 0; j < 8; j++) { // Columns
                if ((i + j) % 2 == 0) {
                    // Even sum of indexes: BLACK
                    playingField[i][j] = new Tile(AvaiableColors.BLACK);
                } else {
                    // Odd sum of indexes: WHITE
                    playingField[i][j] = new Tile(AvaiableColors.WHITE);
                }
            }
        }
        setChessPieces();
        // update potentially attacked zones
    }

    private void setChessPieces() {
        setPawns();
        setRooks();
        setKnights();
        setBishops();
        setQueens();
        setKings();
    }
    private void setPawns() {
        for (int i = 0; i < 8; i++) {
            playingField[6][i].setPiece(new Pawn(AvaiableColors.WHITE));
            playingField[1][i].setPiece(new Pawn(AvaiableColors.BLACK));
        }
    }

    private void setRooks() {
        playingField[0][0].setPiece(new Rook(AvaiableColors.BLACK));
        playingField[0][7].setPiece(new Rook(AvaiableColors.BLACK));
        playingField[7][0].setPiece(new Rook(AvaiableColors.WHITE));
        playingField[7][7].setPiece(new Rook(AvaiableColors.WHITE));


    }

    private void setKnights() {
        playingField[0][1].setPiece(new Knight(AvaiableColors.BLACK));
        playingField[0][6].setPiece(new Knight(AvaiableColors.BLACK));
        playingField[7][1].setPiece(new Knight(AvaiableColors.WHITE));
        playingField[7][6].setPiece(new Knight(AvaiableColors.WHITE));
    }

    private void setBishops() {
        playingField[0][2].setPiece(new Bishop(AvaiableColors.BLACK));
        playingField[0][5].setPiece(new Bishop(AvaiableColors.BLACK));
        playingField[7][2].setPiece(new Bishop(AvaiableColors.WHITE));
        playingField[7][5].setPiece(new Bishop(AvaiableColors.WHITE));
    }

    private void setQueens() {
        playingField[0][3].setPiece(new Queen(AvaiableColors.BLACK));
        playingField[7][3].setPiece(new Queen(AvaiableColors.WHITE));
    }

    private void setKings() {
        playingField[0][4].setPiece(new King(AvaiableColors.BLACK));
        playingField[7][4].setPiece(new King(AvaiableColors.WHITE));
    }
    
}
