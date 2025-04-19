package org.example.chessboardElements.pieces;

import java.awt.*;
import java.awt.image.BufferedImage;

// import com.badlogic.gdx.graphics.Color; // importing libGDX

import org.example.chessboardElements.ChessPieceColors;


public enum ChessPieceVisuals {
    // avaiable piece types
    KING('♚','♔'),
    QUEEN('♛','♕'),
    ROOK('♜','♖'),
    BISHOP('♝','♗'),
    KNIGHT('♞','♘'),
    PAWN('♟','♙');

    // Pole przechowujące symbol figury
    // Czcionki opisujące figure
    private final char symbol;
    private final char shadow;
    // Obrazy opisujące figure
    private final BufferedImage whiteImage;
    private final BufferedImage blackImage;

    // Konstruktor enum
    ChessPieceVisuals(char symbol, char shadow) {
        this.symbol = symbol;
        this.shadow = shadow;
        this.whiteImage = generateImage(symbol, shadow, ChessPieceColors.WHITE);
        this.blackImage = generateImage(symbol, shadow, ChessPieceColors.BLACK);
    }

    private static BufferedImage generateImage(char symbol, char shadow, ChessPieceColors pieceColor) {
        int element_size = 64;
        int offset = 1;

        // If piece is white then background black.
        // Reverse color for background
        ChessPieceColors backgroudColor;
        if(pieceColor == ChessPieceColors.WHITE){
            backgroudColor = ChessPieceColors.BLACK;
        }
        else{
            backgroudColor = ChessPieceColors.WHITE;
        }
        // Base propeties
        BufferedImage pieceImage = new BufferedImage( element_size,element_size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D renderingClass = pieceImage.createGraphics();


        // DLA TŁA
        // Ustawienie koloru i wartości czcionki
        renderingClass.setColor(backgroudColor.getPieceColor());
        renderingClass.setFont(new Font("Arial Unicode MS", Font.BOLD, element_size/2 ));


        FontMetrics charDimensions = renderingClass.getFontMetrics();
        // Calculate marigin around piece
        int x = (element_size - charDimensions.charWidth(symbol)) / 2;
        int y = (element_size - charDimensions.getHeight()) /2 + charDimensions.getAscent();

        // DLA OTOCZKI
        renderingClass.drawString(String.valueOf(symbol), x+offset, y);
        renderingClass.drawString(String.valueOf(symbol), x-offset, y);
        renderingClass.drawString(String.valueOf(symbol), x, y+offset);
        renderingClass.drawString(String.valueOf(symbol), x, y-offset);

        // DLA WYPELNIENIA
        renderingClass.setColor(pieceColor.getPieceColor());
        renderingClass.drawString(String.valueOf(symbol), x, y);

        // DLA CIENIA
        renderingClass.setColor(pieceColor.getShadowColor());
        renderingClass.drawString(String.valueOf(shadow),x,y);

        // zwolnienie sterownikow
        renderingClass.dispose();
        return pieceImage;
    }

    // Getter zwracający symbol figury
    public char getSymbol() {
        return symbol;
    }

    public BufferedImage getImage(ChessPieceColors color) {
        if (color == ChessPieceColors.WHITE) {
            return whiteImage;
        } else {
            return blackImage;
        }
    }
}
