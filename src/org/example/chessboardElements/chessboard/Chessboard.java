package org.example.chessboardElements.chessboard;

import org.example.chessboardElements.ChessPieceColors;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.pieces.pieceType.*;

import java.util.ArrayList;

public class Chessboard {
    private Tile[][] playingField;
    /** reference to every piece that's on Tile[][] playingField */
    private ArrayList<ChessPiece> ammountOfMaterial = new ArrayList<>();

    public Chessboard(int tileSizeInPixels) {
        buildChessboard(tileSizeInPixels);
    }

    public Tile[][] getPlayingField() {
        return playingField;
    }

    /** get Tile from corresponding X, Y */
    public Tile getTile(int row, int column) {
        return playingField[row][column];

    }

    /** reset the button to deafult color*/
    public void resetButtonColors() {
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField[i].length; j++) {
                playingField[i][j].resetButtonColor();
            }
        }
    }

    public ArrayList<ChessPiece> getAmountOfMaterial() {
        return ammountOfMaterial;
    }

    public ChessPiece removePieceFromMaterial(ChessPiece chessPiece) {
        ammountOfMaterial.remove(chessPiece);
        return chessPiece;
    }

    /** Setup tiles, buttons, chessPieces. */
    private void buildChessboard(int size) {
        playingField = new Tile[8][8];
        for (int i = 0; i < 8; i++) { // Rows
            for (int j = 0; j < 8; j++) { // Columns
                if ((i + j) % 2 == 0) {
                    // Even sum of indexes: WHITE
                    playingField[i][j] = new Tile(ChessPieceColors.WHITE, i, j, size);
                } else {
                    // Odd sum of indexes: BLACK
                    playingField[i][j] = new Tile(ChessPieceColors.BLACK, i, j, size);
                }
            }
        }
        setUpChessPieces(); // Initializes the board with the default chess setup.
        //piecesLookAround();
    }

    private void setUpChessPieces() {
        setQueens();
        setPawns();
        setRooks();
        setBishops();
        setKnights();
        setKings();
    }

    private void piecesLookAround() {
        for (ChessPiece piece : ammountOfMaterial) {
            piece.lookAround();
            piece.updateTileObservers();
        }
    }

    // set Up Pieces By Type
    private void setPawns() {
        for (int i = 0; i < 8; i++) {
            Pawn whitePawn = new Pawn(ChessPieceColors.WHITE, this);
            Pawn blackPawn = new Pawn(ChessPieceColors.BLACK, this);
            playingField[i][6].setPiece(whitePawn);
            playingField[i][1].setPiece(blackPawn);
            ammountOfMaterial.add(whitePawn);
            ammountOfMaterial.add(blackPawn);
        }
    }

    private void setRooks() {
        Rook blackRook1 = new Rook(ChessPieceColors.BLACK, this);
        Rook blackRook2 = new Rook(ChessPieceColors.BLACK, this);
        Rook whiteRook1 = new Rook(ChessPieceColors.WHITE, this);
        Rook whiteRook2 = new Rook(ChessPieceColors.WHITE, this);
        playingField[0][0].setPiece(blackRook1);
        playingField[7][0].setPiece(blackRook2);
        playingField[0][7].setPiece(whiteRook1);
        playingField[7][7].setPiece(whiteRook2);
        ammountOfMaterial.add(blackRook1);
        ammountOfMaterial.add(blackRook2);
        ammountOfMaterial.add(whiteRook1);
        ammountOfMaterial.add(whiteRook2);
    }

    private void setKnights() {
        Knight blackKnight1 = new Knight(ChessPieceColors.BLACK, this);
        Knight blackKnight2 = new Knight(ChessPieceColors.BLACK, this);
        Knight whiteKnight1 = new Knight(ChessPieceColors.WHITE, this);
        Knight whiteKnight2 = new Knight(ChessPieceColors.WHITE, this);
        playingField[1][0].setPiece(blackKnight1);
        playingField[6][0].setPiece(blackKnight2);
        playingField[1][7].setPiece(whiteKnight1);
        playingField[6][7].setPiece(whiteKnight2);
        ammountOfMaterial.add(blackKnight1);
        ammountOfMaterial.add(blackKnight2);
        ammountOfMaterial.add(whiteKnight1);
        ammountOfMaterial.add(whiteKnight2);
    }

    private void setBishops() {
        Bishop blackBishop1 = new Bishop(ChessPieceColors.BLACK, this);
        Bishop blackBishop2 = new Bishop(ChessPieceColors.BLACK, this);
        Bishop whiteBishop1 = new Bishop(ChessPieceColors.WHITE, this);
        Bishop whiteBishop2 = new Bishop(ChessPieceColors.WHITE, this);
        playingField[2][0].setPiece(blackBishop1);
        playingField[5][0].setPiece(blackBishop2);
        playingField[2][7].setPiece(whiteBishop1);
        playingField[5][7].setPiece(whiteBishop2);
        ammountOfMaterial.add(blackBishop1);
        ammountOfMaterial.add(blackBishop2);
        ammountOfMaterial.add(whiteBishop1);
        ammountOfMaterial.add(whiteBishop2);
    }

    private void setQueens() {
        Queen blackQueen = new Queen(ChessPieceColors.BLACK, this);
        Queen whiteQueen = new Queen(ChessPieceColors.WHITE, this);
        playingField[3][0].setPiece(blackQueen);
        playingField[3][7].setPiece(whiteQueen);
        ammountOfMaterial.add(blackQueen);
        ammountOfMaterial.add(whiteQueen);
    }

    private void setKings() {
        King blackKing = new King(ChessPieceColors.BLACK, this);
        King whiteKing = new King(ChessPieceColors.WHITE, this);
        playingField[4][0].setPiece(blackKing);
        playingField[4][7].setPiece(whiteKing);
        ammountOfMaterial.add(blackKing);
        ammountOfMaterial.add(whiteKing);
    }

}
