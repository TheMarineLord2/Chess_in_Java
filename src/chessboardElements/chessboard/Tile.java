package chessboardElements.chessboard;

import chessboardElements.pieces.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.util.List;


// klasa tile przechowuje metody dostępne zawsze dla zwykłego pola oraz te służące do renderowania
// klasy pieces rozszerzają klasę Tile o figury szachowe na niej występujące, ich wizualna reprezentację itd;
public class Tile {
    private JButton fieldButton;
    private Color tileColor;
    private ChessPiece chessPiece;
    // updates every time piece moved, to EVERY potential field.
    // looking for checks.
    // If King is on a field Potentially attacked by certain Chess piece,
    // We shoulld see if there will be piece left after moovea
    private List<ChessPiece> listOfPotentialAttackers;

    // KONSTRUTORY
    private Tile() {}
    public Tile(AvaiableColors avaiableColors) {
        setTileColor(avaiableColors);
    }

    // metody publiczne
    /*public void changeLocationOfFieldButton(int x, int y){
        fieldButton.setLocation(x,y);
    }*/
    public void setPiece(ChessPiece chessPiece){this.chessPiece = chessPiece;}
    public ChessPiece getPiece() {return chessPiece;}
    public Color getColor(){return fieldButton.getBackground();}

    public JButton getFieldButton(int row, int column, int size) {
        if (fieldButton == null) {
            fieldButton = new JButton();
            fieldButton.setLayout(new GridLayout(1, 1));
            fieldButton.setBounds(row * size, column * size, size, size);
            fieldButton.setBackground(tileColor);
            
            showChessPiece();
            
            fieldButton.setVisible(true);
        }
        return fieldButton;
    }

    public void showChessPiece() {
        if (chessPiece != null && fieldButton != null) {
            fieldButton.setText(String.valueOf(chessPiece.getVisualRepresentation()));
            fieldButton.setForeground(chessPiece.getColor()); // Assumes chessPiece has a getColor() method.
            // Arial Unicode MS is the coding that supports characters a
            fieldButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 50));
        } else if (fieldButton != null) {
            fieldButton.setText("");
        }
    }

    // metody prywatne
    private void setTileColor(AvaiableColors avaiableColors) {
        this.tileColor = avaiableColors.getTileColor();
    }
}
