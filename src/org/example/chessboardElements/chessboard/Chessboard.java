package org.example.chessboardElements.chessboard;

import org.example.chessboardElements.AvaiableChessColors;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.pieces.pieceType.*;

import java.util.ArrayList;

/**
 * This class represents a chessboard used in a chess game.
 * It includes a grid layout (8x8 by default), methods to manage tiles and chess pieces on the board,
 * and functionality to initialize and reset the game.
 */
public class Chessboard {

    /**
     * Represents the grid of the chessboard, consisting of individual tiles.
     */
    private Tile[][] playingField;

    /**
     * Stores all the chess pieces currently present on the board, allowing for quick access and tracking.
     */
    private ArrayList<ChessPiece> ammountOfMaterial = new ArrayList<>();

    /**
     * Constructs a Chessboard and initializes it with the specified tile size, 
     * also setting up the chess pieces on their initial positions.
     *
     * @param size the dimensions (in pixels) of each tile on the chessboard.
     */
    public Chessboard(int size) {
        buildChessboard(size);
    }

    // metody publiczne
    // zwraca plansze 8x8

    /**
     * Returns the 2D array representing the chessboard's tiles.
     *
     * @return a 2D array containing all tiles on the chessboard.
     */
    public Tile[][] getPlayingField() {
        return playingField;
    }

    /**
     * Provides access to a specific tile on the chessboard based on its row and column indices.
     *
     * @param row    the row index of the tile.
     * @param column the column index of the tile.
     * @return the tile at the specified position.
     */
    public Tile getTile(int row, int column) {
        return playingField[row][column];
    }

    /**
     * Resets the visual color of every tile on the chessboard.
     * This is typically used to remove highlights caused during gameplay.
     */
    public void resetTileButtonColors() {
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField[i].length; j++) {
                playingField[i][j].resetTileButtonColor();
            }
        }
    }

    /**
     * Gets the list of all chess pieces currently on the board.
     *
     * @return an ArrayList containing all chess pieces present on the chessboard.
     */
    public ArrayList<ChessPiece> getAmountOfMaterial() {
        return ammountOfMaterial;
    }

    /**
     * Removes a specific chess piece from the board's material tracking list.
     *
     * @param chessPiece the chess piece to be removed.
     */
    public void removePieceFromMaterial(ChessPiece chessPiece) {
        ammountOfMaterial.remove(chessPiece);
    }

    /**
     * Builds the initial layout of the chessboard using an 8x8 grid of tiles.
     * Each tile alternates in color based on its position, similar to real chessboards.
     *
     * @param size the dimensions (in pixels) of each tile on the chessboard.
     */
    private void buildChessboard(int size) {
        playingField = new Tile[8][8];
        for (int i = 0; i < 8; i++) { // Rows
            for (int j = 0; j < 8; j++) { // Columns
                if ((i + j) % 2 == 0) {
                    // Even sum of indexes: BLACK
                    playingField[i][j] = new Tile(AvaiableChessColors.WHITE, i, j, size);
                } else {
                    // Odd sum of indexes: WHITE
                    playingField[i][j] = new Tile(AvaiableChessColors.BLACK, i, j, size);
                }
            }
        }
        setChessPieces(); // Initializes the board with the default chess setup.
        // update potentially attacked zones
    }

    /**
     * Places all chess pieces onto the board. This includes all pawns, rooks, bishops, knights, queens, and kings.
     * Each piece is placed in its standard position according to chess rules.
     */
    private void setChessPieces() {
        setQueens();
        setPawns();
        setRooks();
        setBishops();
        setKnights();
        setKings();
    }

    private void setPawns() {
        for (int i = 0; i < 8; i++) {
            Pawn whitePawn = new Pawn(AvaiableChessColors.WHITE);
            Pawn blackPawn = new Pawn(AvaiableChessColors.BLACK);
            playingField[i][6].setPiece(whitePawn);
            playingField[i][1].setPiece(blackPawn);
            ammountOfMaterial.add(whitePawn);
            ammountOfMaterial.add(blackPawn);
        }
    }

    private void setRooks() {
        Rook blackRook1 = new Rook(AvaiableChessColors.BLACK);
        Rook blackRook2 = new Rook(AvaiableChessColors.BLACK);
        Rook whiteRook1 = new Rook(AvaiableChessColors.WHITE);
        Rook whiteRook2 = new Rook(AvaiableChessColors.WHITE);
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
        Knight blackKnight1 = new Knight(AvaiableChessColors.BLACK);
        Knight blackKnight2 = new Knight(AvaiableChessColors.BLACK);
        Knight whiteKnight1 = new Knight(AvaiableChessColors.WHITE);
        Knight whiteKnight2 = new Knight(AvaiableChessColors.WHITE);
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
        Bishop blackBishop1 = new Bishop(AvaiableChessColors.BLACK);
        Bishop blackBishop2 = new Bishop(AvaiableChessColors.BLACK);
        Bishop whiteBishop1 = new Bishop(AvaiableChessColors.WHITE);
        Bishop whiteBishop2 = new Bishop(AvaiableChessColors.WHITE);
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
        Queen blackQueen = new Queen(AvaiableChessColors.BLACK);
        Queen whiteQueen = new Queen(AvaiableChessColors.WHITE);
        playingField[3][0].setPiece(blackQueen);
        playingField[3][7].setPiece(whiteQueen);
        ammountOfMaterial.add(blackQueen);
        ammountOfMaterial.add(whiteQueen);
    }

    private void setKings() {
        King blackKing = new King(AvaiableChessColors.BLACK);
        King whiteKing = new King(AvaiableChessColors.WHITE);
        playingField[4][0].setPiece(blackKing);
        playingField[4][7].setPiece(whiteKing);
        ammountOfMaterial.add(blackKing);
        ammountOfMaterial.add(whiteKing);
    }

}
