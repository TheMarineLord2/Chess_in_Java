package org.example.mainControllers.gameControlls;

import org.example.objectsAndElements.ChessPieceColors;
import org.example.objectsAndElements.SpecTileFunc;
import org.example.objectsAndElements.chessboard.Chessboard;
import org.example.objectsAndElements.chessboard.Tile;
import org.example.objectsAndElements.pieces.ChessPiece;
import org.example.objectsAndElements.pieces.pieceType.King;
import org.example.objectsAndElements.pieces.pieceType.Pawn;

// GameInstance sprawuje bezpośredni nadzór nad
// powołaniem gry, przypisaniem graczy, przebiegu
// tur oraz nad innymi kontrollerami.
public class GameInstance{

    // vartości kontrolowane przez instancje
    private Chessboard chessboard;
    private int turn_counter;
    private Player whitePlayer;
    private Player blackPlayer;
    private ChessPieceColors currentlyPlaying;
    private int numberOfInteractablePieces = 1;
    private Pawn enPassantPawn; // pawn that jumped 2 places last turn
    private GameOperator gameOperator;

    private GameInstance()
    {
        this.currentlyPlaying = null;
    }

    public GameInstance(Player whitePlayer, Player blackPlayer, int tileSizeInPixels)
    {
        System.out.println("Creating new game instance");
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.chessboard = new Chessboard(tileSizeInPixels);
        this.turn_counter = -1;
        this.enPassantPawn = null; // Initialize enPassantPawn to null\
        passControlsToAnotherPlayer();
    }

    public Player getPlayerObject(ChessPieceColors playerColor)
    {
        if(playerColor == ChessPieceColors.WHITE )
        {
            return whitePlayer;
        }
        else if (playerColor == ChessPieceColors.BLACK)
        {
            return blackPlayer;
        }
        else
        {
            return null;
        }
    }
    public Chessboard getChessboard()
    {
        return chessboard;
    }
    public int getTurnCounter()
    {
        return turn_counter;
    }

    public void takeATurn()
    {
        chessboard.clearEnPassantMarkersFor(currentlyPlaying);
        chessboard.checkPromotionFor(currentlyPlaying);
        // --- beginning of a new turn
        passControlsToAnotherPlayer();
        if(isGameOver())
        {
               gameOperator.showEndGameMessage("Checkmate!");
        }
        // wait for further instructions
    }

    private void passControlsToAnotherPlayer()
    {
        turn_counter++;
        if(turn_counter%2==0)
        {
            setControlsTo(ChessPieceColors.WHITE);
        }
        else if (turn_counter%2==1)
        {
            setControlsTo(ChessPieceColors.BLACK);
        }
        System.out.println("Currently Playing: " + currentlyPlaying);
        chessboard.print();
    }

    private void setControlsTo(ChessPieceColors color)
    {
        this.currentlyPlaying = color;
        makeChesPiecesOfThisColourClickable();
        System.out.println("Passing controls to: " + currentlyPlaying);
    }

    private void makeChesPiecesOfThisColourClickable()
    {
        numberOfInteractablePieces = 0;

        Tile[][] tiles = chessboard.getPlayingField();
        for (Tile[] tile : tiles)
        {
            for (Tile value : tile)
            {
                var piece = value.getPiece();
                if (piece != null && piece.getColor() == currentlyPlaying && piece.hasAvailableMoves())
                {
                    numberOfInteractablePieces++;
                    value.setInteractable(true);
                }
                else
                {
                    value.setInteractable(false);
                }
            }
        }
    }


    private boolean isCheckmate()
    {
        // Get the king of the currently playing color
        ChessPiece king = chessboard.getAmountOfMaterial().stream().filter(piece -> piece instanceof King && piece.getColor() == currentlyPlaying).findFirst().orElse(null);

        if (king == null)
        {
            throw new IllegalStateException("No king found for the current player.");
        }

        // Check if the king's current tile is threatened
        Tile kingTile = king.getHomeTile();
        if (kingTile != null && !kingTile.isSafeToStepOn(currentlyPlaying))
        {
            // if the king tile is threatened
            if (!king.hasAvailableMoves())
            {
                // If king has no safe moves
                // count number of attackers
                int numberOfAttackers = 0;
                ChessPiece lastAttacker = null;
                for (ChessPiece tileObserver : kingTile.getListOfObservers()){
                    if (tileObserver.getColor()!=king.getColor() && tileObserver.getImportantTiles().get(SpecTileFunc.ENEMY_TILE).contains(kingTile)){
                        numberOfAttackers++;
                        lastAttacker = tileObserver;
                    }
                }

                if (numberOfAttackers == 1)
                {
                    // see if the attacker can be killed
                    for (ChessPiece observer : lastAttacker.getHomeTile().getListOfObservers())
                    {
                        // for each piece looking on the attacker
                        if (observer.getColor() == king.getColor() && observer.getImportantTiles().get(SpecTileFunc.ENEMY_TILE).contains(lastAttacker.getHomeTile()) && !observer.isPinnedTo(kingTile) )
                        {
                            // means we could kill HIM!!! RAAAARGHHHHH!!!!
                            return false;
                        }
                    }
                    // see if the attack can be stopped
                    for (Tile t : chessboard.tilesInStraightLine(kingTile, lastAttacker.getHomeTile()))
                    {
                        // for each tile on the way
                        for(ChessPiece observer : t.getListOfObservers())
                        {
                            // get observer

                            if(observer.getColor() == king.getColor() && !observer.isPinnedTo(kingTile))
                            {
                                // if it's a not pinned ally
                                if(observer.getImportantTiles().get(SpecTileFunc.AVAILABLE_TILE).contains(t))
                                {
                                    // if it can move there
                                    return false;
                                }
                            }
                        }
                    }

                }
                return true;
            }
        }
        return false;
    }

    private boolean noMovesLeft()
    {
        return numberOfInteractablePieces == 0;
    }

    // private void checkThreefoldRepetition(){}
    private boolean is50MoveRule()
    {
        // Check if the turn counter equals 100 for the 50-move rule
        return turn_counter == 100;
    }

    private boolean isInsufficientMaterial()
    {
        return chessboard.isInsufficientMaterial();
    }

    private void setEnPassantPawn(Pawn pawn)
    {
        this.enPassantPawn = pawn;
    }
    private void unCheckEnPassant()
    {
        enPassantPawn = null; // Reset the en passant pawn at the end of the turn
    }

    public boolean isGameOver()
    {
        return is50MoveRule() || noMovesLeft() || isInsufficientMaterial() || isCheckmate() ;
    }

    public ChessPieceColors getCurrentPlayerColor()
    {
        return currentlyPlaying;
    }
    
}
