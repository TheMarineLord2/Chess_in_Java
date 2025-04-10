package org.example.mainControllers.gameControlls;

import org.example.chessboardElements.AvaiableChessColors;
import org.example.chessboardElements.chessboard.Chessboard;
import org.example.chessboardElements.pieces.ChessPiece;
import org.example.chessboardElements.pieces.pieceType.Bishop;
import org.example.chessboardElements.pieces.pieceType.King;
import org.example.chessboardElements.pieces.pieceType.Knight;
import org.example.chessboardElements.pieces.pieceType.Pawn;

import java.util.ArrayList;

// GameInstance sprawuje bezpośredni nadzór nad
// powołaniem gry, przypisaniem graczy, przebiegu
// tur oraz nad innymi kontrollerami.
public class GameInstance {

    // vartości kontrolowane przez instancje
    private Chessboard chessboard;
    private int turn_counter;
    private Player white;
    private Player black;
    private AvaiableChessColors currentlyPlaying;
    private int numberOfinteractablePieces;
    private Pawn enPassantPawn; // pawn that jumped 2 places last turn

    // zablokowanie domyślnego konstruktora
    private GameInstance() {
        this.currentlyPlaying = null;
    }

    // konstruktor właściwy
    public GameInstance(Player white, Player black, int chessboardSize) {
        this.white = white;
        this.black = black;
        this.chessboard = new Chessboard(chessboardSize);
        this.turn_counter = 0;
        this.enPassantPawn = null; // Initialize enPassantPawn to null
    }

    public Player getPlayer(int playerNumber){
        if(playerNumber == 1){
            return white;
        }else if(playerNumber == 2){
            return black;
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
            setControlsTo(AvaiableChessColors.BLACK);
        }
        else {
            setControlsTo(AvaiableChessColors.WHITE);
        }
        getThroughWinconditions();
        unCheckEnPassant();
        
    }

    private void setControlsTo(AvaiableChessColors color) {
        this.currentlyPlaying = color;
        makeChesPiecesOfThisColourClickable();
    }

    private void makeChesPiecesOfThisColourClickable() {
        // Clear the list of interactable pieces and available material.
        numberOfinteractablePieces = 0;
        var tiles = chessboard.getPlayingField();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                var piece = tiles[i][j].getPiece();
                if (piece != null && piece.getColor() == currentlyPlaying) {
                    piece.setInteractableIfPossibleMoves(true);
                    if(piece.isTrulyInteractable()){
                        numberOfinteractablePieces++;
                    }
                } else tiles[i][j].setInteractable(false);
            }
        }
    }

    private void getThroughWinconditions(){
        // checkCheckmate();
        checkStalemate();
        // checkThreefoldRepetition();
        check50MoveRule();
        // if piece taken:
        checkInsufficientMaterial();
    }
    private void checkCheckmate(){
        
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
                    if (king.getColor() == AvaiableChessColors.WHITE) whiteKing = true;
                    else if (king.getColor() == AvaiableChessColors.BLACK) blackKing = true;
                    break;
                case Bishop bishop:
                    if (bishop.getColor() == AvaiableChessColors.WHITE) whiteBishops++;
                    else blackBishops++;
                    break;
                case Knight knight:
                    if (knight.getColor() == AvaiableChessColors.WHITE) whiteKnights++;
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
        System.out.println("Game won by " + (currentlyPlaying == AvaiableChessColors.WHITE ? "Black" : "White"));
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
