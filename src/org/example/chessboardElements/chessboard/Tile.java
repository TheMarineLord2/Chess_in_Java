package org.example.chessboardElements.chessboard;

import org.example.chessboardElements.AvaiableColors;
import org.example.chessboardElements.pieces.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;


// klasa tile przechowuje metody dostępne zawsze dla zwykłego pola oraz te służące do renderowania
// klasy pieces rozszerzają klasę Tile o figury szachowe na niej występujące, ich wizualna reprezentację itd;
public class Tile {
    private JButton button;
    private Color tileColor;
    private ChessPiece chessPiece;
    // updates every time piece moved, to EVERY potential field.
    // looking for checks.
    // If King is on a field Potentially attacked by certain Chess piece,
    // We shoulld see if there will be piece left after moovea
    private List<ChessPiece> listOfPotentialAttackers;

    // KONSTRUTORY
    private Tile() {}
    public Tile(AvaiableColors avaiableColors, int row, int column, int size) {
        setTileColor(avaiableColors);
        setUpFieldButtonProperties(row,column,size);
        setUpFieldButtonListeners();
    }

    // metody publiczne
    /*public void changeLocationOfFieldButton(int x, int y){
        fieldButton.setLocation(x,y);
    }*/
    public void setPiece(ChessPiece chessPiece){
        this.chessPiece = chessPiece;

    }
    public ChessPiece getPiece() {return chessPiece;}
    public Color getColor(){return button.getBackground();}

    public JButton getButton() {
        return button;
    }

    public void showChessPiece() {
        if (chessPiece != null && button != null) {
            BufferedImage chessPieceImage = chessPiece.getChessPieceVisuals();
            // Image scaledImage = chessPieceImage.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(chessPieceImage));
        } else if (button != null) {
            button.setIcon(null);
        }
    }

    // metody prywatne
    private void setTileColor(AvaiableColors avaiableColors) {
        this.tileColor = avaiableColors.getTileColor();
    }
    private void setUpFieldButtonProperties(int row, int column, int size) {
        button = new JButton();
        button.setBackground(tileColor);
        button.setLayout(new GridLayout(1, 1));
        button.setBounds(row * size, column * size, size, size);
        button.setVisible(true);
    }

    private void setUpFieldButtonListeners() {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (chessPiece != null) {
                    BufferedImage chessPieceImage = chessPiece.getChessPieceVisuals();
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    Cursor cursor = toolkit.createCustomCursor(
                            chessPieceImage, new Point(0, 0), "chessPieceCursor"
                    );
                    button.setCursor(cursor);
                }
                }
            });
    }
}
