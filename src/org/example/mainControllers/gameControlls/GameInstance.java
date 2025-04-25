package org.example.mainControllers.gameControlls;

import org.example.chessboardElements.ChessPieceColors;
import org.example.chessboardElements.SpecTileFunc;
import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.chessboard.Tile;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.pieces.pieceType.Bishop;
import org.example.chessboardElements.pieces.pieceType.King;
import org.example.chessboardElements.pieces.pieceType.Knight;
import org.example.chessboardElements.pieces.pieceType.Pawn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// GameInstance sprawuje bezpośredni nadzór nad
// powołaniem gry, przypisaniem graczy, przebiegu
// tur oraz nad innymi kontrollerami.
public class GameInstance {

    // vartości kontrolowane przez instancje
    private Chessboard chessboard;
    private int turn_counter;
    private Player whitePlayer;
    private Player blackPlayer;
    private ChessPieceColors currentlyPlaying;
    private int numberOfinteractablePieces;
    private Pawn enPassantPawn; // pawn that jumped 2 places last turn

    // zablokowanie domyślnego konstruktora
    private GameInstance() {
        this.currentlyPlaying = null;
    }

    // konstruktor właściwy
    public GameInstance(Player whitePlayer, Player blackPlayer, int tileSizeInPixels) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.chessboard = new Chessboard(tileSizeInPixels);
        this.turn_counter = 0;
        this.enPassantPawn = null; // Initialize enPassantPawn to null
    }

    public Player getPlayerObject(ChessPieceColors playerColor){
        if(playerColor == ChessPieceColors.WHITE ){
            return whitePlayer;
        }else if(playerColor == ChessPieceColors.BLACK){
            return blackPlayer;
        }else{
            return null;
        }
    }
    public Chessboard getChessboard(){
        return chessboard;
    }
    public int getTurnCounter(){
        return turn_counter;
    }

    public void takeATurn(){
        if(turn_counter%2==0){
            setControlsTo(ChessPieceColors.BLACK);
        }
        else {
            setControlsTo(ChessPieceColors.WHITE);
        }
        getThroughConditions();
        unCheckEnPassant();
        
    }

    private void setControlsTo(ChessPieceColors color) {
        this.currentlyPlaying = color;
        makeChesPiecesOfThisColourClickable();
    }

    private void makeChesPiecesOfThisColourClickable() {
        // Clear the list of interactable pieces and available material.
        numberOfinteractablePieces = 0;
        var tiles = chessboard.getPlayingField();
        for (Tile[] tile : tiles) {
            for (Tile value : tile) {
                var piece = value.getPiece();
                if (piece != null && piece.getColor() == currentlyPlaying) {
                    if (piece.hasAvailableMoves()) {
                        numberOfinteractablePieces++;
                        value.setInteractable(true);
                    }
                } /* if wrng colr, null, noMoves4ThisPiece */ else value.setInteractable(false);
            }
        }
    }

    private void getThroughConditions(){
        // checkCheckmate();
        checkStalemate();
        // checkThreefoldRepetition();
        check50MoveRule();
        // if piece taken:
        checkInsufficientMaterial();
    }

    /** if king not threatened == false
     * else king look around and see if there are moves avaiable
     * AVAILABLE ALLIES TO COVER FOR KING ARE NOT YET SEEN */
    private void checkCheckmate() {
        // Get the king of the currently playing color
        ChessPiece king = chessboard.getAmountOfMaterial().stream()
                .filter(piece -> piece instanceof King && piece.getColor() == currentlyPlaying)
                .findFirst()
                .orElse(null);

        if (king == null) {
            throw new IllegalStateException("No king found for the current player.");
        }

        // Check if the king's current tile is threatened
        Tile kingTile = king.getHomeTile();
        if (kingTile != null && !kingTile.isSafeToStepOn(currentlyPlaying)) {
            // Check movements available to the king
            Map<SpecTileFunc, List<Tile>> potentialMoves = king.getImportantTiles();
            boolean hasSafeMove = potentialMoves.get(SpecTileFunc.AVAILABLE_TILE) != null
                                || potentialMoves.get(SpecTileFunc.ENEMY_TILE)!= null;
            // If no safe moves are available, declare a checkmate
            if (!hasSafeMove) {
                gameLost();
            }
        }
    }

    private void checkStalemate() {
        if (numberOfinteractablePieces == 0) {
            drawDue("Stalemate: no possible moves for the current player");
        }
    }

    // private void checkThreefoldRepetition(){}
    private void check50MoveRule() {
        // Check if the turn counter equals 100 for the 50-move rule
        if (turn_counter == 100) {
            System.out.println("Draw: 50-move rule triggered");
        }
    }

    private void checkInsufficientMaterial() {
        int whiteBishops = 0, blackBishops = 0;
        int whiteKnights = 0, blackKnights = 0;
        boolean whiteKing = false, blackKing = false;
        boolean otherPiecesExist = false;
        ArrayList<ChessPiece> ammountOfMaterial = chessboard.getAmountOfMaterial();

        // Loop through the available material list to identify pieces
        label:
        for (ChessPiece piece : ammountOfMaterial) {
            switch (piece) {
                case King king:
                    if (king.getColor() == ChessPieceColors.WHITE) whiteKing = true;
                    else if (king.getColor() == ChessPieceColors.BLACK) blackKing = true;
                    break;
                case Bishop bishop:
                    if (bishop.getColor() == ChessPieceColors.WHITE) whiteBishops++;
                    else blackBishops++;
                    break;
                case Knight knight:
                    if (knight.getColor() == ChessPieceColors.WHITE) whiteKnights++;
                    else blackKnights++;
                    break;
                case null:
                default:
                    otherPiecesExist = true;
                    break label;
            }
        }
        // Check scenarios for insufficient material
        if (!otherPiecesExist) {
            if (whiteKing && blackKing && (whiteBishops + blackBishops + whiteKnights + blackKnights) == 0) {
                drawDue("King vs. King");
            } else if (whiteKing && blackKing && (whiteBishops + blackBishops == 1) && (whiteKnights + blackKnights) == 0) {
                drawDue("King and Bishop vs. King");
            } else if (whiteKing && blackKing && (whiteKnights + blackKnights) == 1 && (whiteBishops + blackBishops) == 0) {
                drawDue("King and Knight vs. King");
            } else if (whiteKing && blackKing && (whiteKnights <= 2) && (blackKnights <= 2) && (whiteBishops + blackBishops) == 0) {
                drawDue("King and two Knights vs. King");

            }
        }
    }
    // Public method to set the en passant pawn reference
    private void gameLost(){
        endGame();
        System.out.println("Game won by " + (currentlyPlaying == ChessPieceColors.WHITE ? "Black" : "White"));
    }
    private void drawDue(String reasonWhy){
        endGame();
        System.out.println("Draw: " + reasonWhy);
    }
    private void endGame(){
        // inform game operator about this4
    }
    private void setEnPassantPawn(Pawn pawn) {
        this.enPassantPawn = pawn;
    }
    private void unCheckEnPassant() {
        enPassantPawn = null; // Reset the en passant pawn at the end of the turn
    }
    
}
