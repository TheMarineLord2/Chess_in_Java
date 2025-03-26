package chessboardElements.chessboard;

import chessboardElements.pieces.Piece;

import javax.swing.*;
import java.awt.*;


// klasa tile przechowuje metody dostępne zawsze dla zwykłego pola oraz te służące do renderowania
// klasy pieces rozszerzają klasę Tile o figury szachowe na niej występujące, ich wizualna reprezentację itd;
public class Tile {
    private JButton fieldButton;
    private Piece piece;
    private int size = 100;

    // KONSTRUTORY
    private Tile() {}
    public Tile(TileColor tileColor) {
        createButton(tileColor);
    }

    // metody publiczne
    /*public void changeLocationOfFieldButton(int x, int y){
        fieldButton.setLocation(x,y);
    }*/
    public void setPiece(Piece piece){this.piece = piece;}
    public Piece getPiece() {return piece;}
    public Color getColor(){return fieldButton.getBackground();}
    public void setButtonCoordinates(int x, int y){fieldButton.setLocation(x,y);}
    public JButton getFieldButton() {return fieldButton;}


    // metody prywatne
    private void createButton(TileColor tileColor) {
        fieldButton = new JButton();
        fieldButton.setSize(size, size);
        fieldButton.setBackground(tileColor.getColor());
        fieldButton.setVisible(true);
    }
}
