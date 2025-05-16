package org.example.objectsAndElements.chessboard;

import org.example.objectsAndElements.ChessPieceColors;
import org.example.objectsAndElements.SpecTileFunc;
import org.example.objectsAndElements.pieces.ChessPiece;
import org.example.objectsAndElements.pieces.pieceType.King;
import org.example.objectsAndElements.pieces.pieceType.Pawn;
import org.example.objectsAndElements.pieces.pieceType.Rook;
import org.example.mainControllers.gameControlls.GameOperator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;


/**
 * The Tile class represents a single square or field on the chessboard.
 * It holds information about its color, position, contained chess piece, and visual representation.
 * It also provides methods for handling interactions and rendering.
 */
public class Tile {
    private JButton button;
    private final ChessPieceColors tileColor;
    private ChessPiece chessPiece = null;
    private final int xValue;
    private final int yValue;
    private final List<ChessPiece> listOfObservers = new java.util.ArrayList<>();
    private final Tile selfReference;
    private final ChessPiece[] referenceCastling = new ChessPiece[2];
    private Pawn referenceEnPassant = null;
    private boolean interactable = false;


    public Tile(ChessPieceColors chessPieceColors, int xValue, int yValue, int size)
    {
        this.tileColor = chessPieceColors;
        this.xValue = xValue;
        this.yValue = yValue;
        this.selfReference = this;
        setUpFieldButtonProperties(size);
        setUpFieldButtonListeners();
    }

    public void setPiece(ChessPiece chessPiece)
    {
        if(chessPiece != null)
        {
            chessPiece.setHomeTileVariable(this);
            this.chessPiece = chessPiece;
        }
        else
        {
            if (this.chessPiece!= null)
            {
                this.chessPiece.setHomeTileVariable(null);
                this.chessPiece = null;
            }
        }
    }

    public ChessPiece getPiece()
    {
        return chessPiece;

    }

    public JButton getButton()
    {
        return button;

    }

    public int getY()
    {
        return yValue;

    }

    public int getX()
    {
        return xValue;

    }

    public ChessPieceColors getOriginalColor()
    {
        return tileColor;

    }

    public void refreshChessPieceIcon()
    {
        if (chessPiece != null && button != null)
        {
            BufferedImage chessPieceImage = chessPiece.getChessPieceVisuals();
            button.setIcon(new ImageIcon(chessPieceImage));
        }
        else if (button != null)
        {
            button.setIcon(null);
        }
    }

    public void paintButton(SpecTileFunc specialTileFunctions)
    {
        setInteractable(true);
        button.setBackground(specialTileFunctions.getColor());
    }

    public void resetButtonColor()
    {
        setInteractable(false);
        button.setBackground(tileColor.getTileColor());
    }

    public void print()
    {
        if(interactable)
        {
            System.out.print("[:X]");
        }
        else
        {
            System.out.print("[:_]");
        }
    }

    private void setUpFieldButtonProperties(int size)
    {
        button = new JButton();
        button.setLayout(new GridLayout(1, 1));
        button.setBounds(xValue * size, yValue * size, size, size);
        resetButtonColor();
        button.setVisible(true);
    }

    private void setUpFieldButtonListeners()
    {
        MouseAdapter interactiveAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.print("Ping - ");
                GameOperator.getGameOperatorInstance().tileClicked(selfReference);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.YELLOW);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(tileColor.getTileColor());
            }
        };
        button.addMouseListener(interactiveAdapter);
    }

    public void setInteractable(boolean b)
    {
        interactable=b;

    }

    public void removeObserver(ChessPiece chessPiece)
    {
        try
        {
            if (chessPiece != null)
            {
                listOfObservers.remove(chessPiece);
            }
        }
        catch (Exception e)
        {
            System.err.println("Failed to remove observer: " + e.getMessage() +
                    "\nIt is possible that the observer is not assigned to the listOfObservers of this tile." +
                    "\nTile Details: Row=" + xValue + ", Column=" + yValue + ", Observers=" + listOfObservers);
        }
    }

    public void addObserver(ChessPiece chessPiece)
    {
        listOfObservers.add(chessPiece);

    }

    public List<ChessPiece> getListOfObservers()
    {
        return listOfObservers;

    }

    public boolean isSafeToStepOn(ChessPieceColors alliedColor)
    {
        for (ChessPiece observer : listOfObservers)
        {
            if (observer.getColor() != alliedColor && observer.getImportantTiles().get(SpecTileFunc.AVAILABLE_TILE).contains(this))
            {
                return false;
            }
        }
        return true;
    }
    public void createCastlingOption(King king, Rook rook)
    {
        referenceCastling[0] = king;
        referenceCastling[1] = rook;

    }

    public Rook getRookForCastling(){
        return (Rook) referenceCastling[1];
    }
    public void removeCastlingOption(ChessPiece caller)
    {
        if (referenceCastling[0] != null)
        {
            referenceCastling[0].getImportantTiles().get(SpecTileFunc.SPECIAL_MOVE).remove(this);
        }
        if (referenceCastling[1] != null)
        {
            referenceCastling[1].getImportantTiles().get(SpecTileFunc.SPECIAL_MOVE).remove(this);
            if (caller instanceof King)
            {
                // if it's the king that moved
                // set the tile as available for the rook
                referenceCastling[0].getImportantTiles().get(SpecTileFunc.AVAILABLE_TILE).add(this);
            }
        }
    }

    public Pawn getReferenceEnPassant(){
        return referenceEnPassant;
    }
    public void setReferenceEnPassant(Pawn pawn){
        referenceEnPassant = pawn;
    }
}
