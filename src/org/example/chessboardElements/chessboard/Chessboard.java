package org.example.chessboardElements.chessboard;

import org.example.chessboardElements.ChessPieceColors;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.pieces.pieceType.*;

import java.util.ArrayList;

public class Chessboard {
    private Tile[][] playingField;
    /** reference to every piece that's on Tile[][] playingField */
    private final ArrayList<ChessPiece> amountOfMaterial = new ArrayList<>();

    public Chessboard(int tileSizeInPixels) {
        buildChessboard(tileSizeInPixels);
    }

    public Tile[][] getPlayingField() {
        return playingField;
    }

    /** get Tile from corresponding X, Y */
    public Tile getTile(int row, int column) {
        if(row>=0 && row<8 && column>=0 && column<8){
            return playingField[row][column];
        }
        return null;
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
        return amountOfMaterial;
    }
 
    /** NOT CHECKING insufficient material for:
     * oddball positions, usually with locked pawns, where no checkmate is possible (this varies with different chess organizations)
     * @param chessPiece
     * @return
     */
    public ChessPiece removePieceFromMaterial(ChessPiece chessPiece) {
        amountOfMaterial.remove(chessPiece);
        isInsufficientMaterial();
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
        piecesLookAround();
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
        for (ChessPiece piece : amountOfMaterial) {
            if (piece.getClass() != King.class) {
                piece.lookAround();
                piece.updateTileObservers();
            }
        }
    }

    // set Up Pieces By Type
    private void setPawns() {
        for (int i = 0; i < 8; i++) {
            Pawn whitePawn = new Pawn(ChessPieceColors.WHITE, this);
            Pawn blackPawn = new Pawn(ChessPieceColors.BLACK, this);
            playingField[i][6].setPiece(whitePawn);
            playingField[i][1].setPiece(blackPawn);
            amountOfMaterial.add(whitePawn);
            amountOfMaterial.add(blackPawn);
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
        amountOfMaterial.add(blackRook1);
        amountOfMaterial.add(blackRook2);
        amountOfMaterial.add(whiteRook1);
        amountOfMaterial.add(whiteRook2);
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
        amountOfMaterial.add(blackKnight1);
        amountOfMaterial.add(blackKnight2);
        amountOfMaterial.add(whiteKnight1);
        amountOfMaterial.add(whiteKnight2);
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
        amountOfMaterial.add(blackBishop1);
        amountOfMaterial.add(blackBishop2);
        amountOfMaterial.add(whiteBishop1);
        amountOfMaterial.add(whiteBishop2);
    }

    private void setQueens() {
        Queen blackQueen = new Queen(ChessPieceColors.BLACK, this);
        Queen whiteQueen = new Queen(ChessPieceColors.WHITE, this);
        playingField[3][0].setPiece(blackQueen);
        playingField[3][7].setPiece(whiteQueen);
        amountOfMaterial.add(blackQueen);
        amountOfMaterial.add(whiteQueen);
    }

    private void setKings() {
        King blackKing = new King(ChessPieceColors.BLACK, this);
        King whiteKing = new King(ChessPieceColors.WHITE, this);
        playingField[4][0].setPiece(blackKing);
        playingField[4][7].setPiece(whiteKing);
        amountOfMaterial.add(blackKing);
        amountOfMaterial.add(whiteKing);
    }

    public boolean isInsufficientMaterial() {
        int whiteBishops = 0, blackBishops = 0;
        int whiteKnights = 0, blackKnights = 0;
        boolean whiteLightSquareBishop = false, whiteDarkSquareBishop = false;
        boolean blackLightSquareBishop = false, blackDarkSquareBishop = false;

        for (ChessPiece piece : amountOfMaterial) {
            // if there are ANY pieces like 3 below. material is always* sufficient.
            // Good practice would be to not call them by type, but create chess piece classes enum
            if (piece.getClass() == Pawn.class || piece.getClass() == Queen.class || piece.getClass() == Rook.class) {
                return false;
            } else {
                if (piece.getClass() == Knight.class) {
                    // summing knights
                    if (piece.getColor() == ChessPieceColors.WHITE) whiteKnights++;
                    else blackKnights++;
                } else if (piece.getClass() == Bishop.class) {
                    if (piece.getColor() == ChessPieceColors.WHITE) {
                        if (piece.getHomeTile().getOriginalColor() == ChessPieceColors.WHITE) {
                            whiteLightSquareBishop = true;
                        } else {
                            whiteDarkSquareBishop = true;
                        }
                        whiteBishops++;
                    } else {
                        if (piece.getHomeTile().getOriginalColor() == ChessPieceColors.WHITE) {
                            blackLightSquareBishop = true;
                        } else {
                            blackDarkSquareBishop = true;
                        }
                        blackBishops++;
                    }
                }
            }
        }
        boolean noSufficientWhiteMaterial = (
                (whiteKnights == 0 && whiteBishops == 0) || /* no material */
                        (whiteKnights == 1 && whiteBishops == 0) || /* 1 knight */
                        (whiteKnights == 0 && whiteBishops == 1) || /* 1 bishop */
                        (whiteBishops == 2 && !(whiteDarkSquareBishop && whiteLightSquareBishop))
        );
        boolean noSufficientBlackMaterial = (
                (blackKnights == 0 && blackBishops == 0) ||
                        (blackKnights == 1 && blackBishops == 0) ||
                        (blackKnights == 0 && blackBishops == 1) ||
                        (blackBishops == 0 && !(blackLightSquareBishop && blackDarkSquareBishop))
        );
        return noSufficientBlackMaterial && noSufficientWhiteMaterial;
    }
}
