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
    private int numberOfInteractablePieces = 1;
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
            turn_counter++;
        }
        else {
            setControlsTo(ChessPieceColors.WHITE);
            turn_counter++;
        }
        //unCheckEnPassant();
        
    }

    private void setControlsTo(ChessPieceColors color) {
        this.currentlyPlaying = color;
        makeChesPiecesOfThisColourClickable();
    }

    private void makeChesPiecesOfThisColourClickable() {
        // Clear the list of interactable pieces and available material.
        numberOfInteractablePieces = 0;
        var tiles = chessboard.getPlayingField();
        for (Tile[] tile : tiles) {
            for (Tile value : tile) {
                var piece = value.getPiece();
                if (piece != null && piece.getColor() == currentlyPlaying) {
                    if (piece.hasAvailableMoves()) {
                        numberOfInteractablePieces++;
                        value.setInteractable(true);
                    }
                } /* if wrng colr, null, noMoves4ThisPiece */ else value.setInteractable(false);
            }
        }
    }

    private void getThroughConditions(){

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

    private boolean noMovesLeft() {
        return numberOfInteractablePieces == 0;
    }

    // private void checkThreefoldRepetition(){}
    private boolean is50MoveRule() {
        // Check if the turn counter equals 100 for the 50-move rule
        return turn_counter == 100;
    }

    private boolean isInsufficientMaterial() {
        return chessboard.isInsufficientMaterial();
    }

    // Public method to set the en passant pawn reference
    private void gameLost(){
        endGame();
        System.out.println("Game won by " + (currentlyPlaying == ChessPieceColors.WHITE ? "Black" : "White"));
    }
    private void itsADrawDue(String reasonWhy){
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

    public boolean isGameOver() {
        // checkCheckmate();
         noMovesLeft();
        // checkThreefoldRepetition();
         is50MoveRule();
        // if piece taken:
        return is50MoveRule() || noMovesLeft() || isInsufficientMaterial();
    }
}
