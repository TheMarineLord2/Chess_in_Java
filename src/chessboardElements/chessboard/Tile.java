package chessboardElements.chessboard;

import chessboardElements.pieces.ChessPiece;
import chessboardElements.pieces.Piece;

import javax.swing.*;


// klasa tile przechowuje metody dostępne zawsze dla zwykłego pola oraz te służące do renderowania
// klasy pieces rozszerzają klasę Tile o figury szachowe na niej występujące, ich wizualna reprezentację itd;
public class Tile {
    private JButton fieldButton;
    private Piece piece;
    private int size = 100;

    // KONSTRUTORY
    private Tile() {}
    public Tile(TileColour tileColour) {
        createButton(tileColour);
    }

    // metody publiczne
    /*public void changeLocationOfFieldButton(int x, int y){
        fieldButton.setLocation(x,y);
    }*/
    public void setPiece(Piece piece){this.piece = piece;}


    // metody prywatne
    private void createButton(TileColour tileColour) {
        fieldButton = new JButton();
        fieldButton.setSize(size, size);
        fieldButton.setBackground(tileColour.getColour());
        fieldButton.setVisible(true);
    }
}
