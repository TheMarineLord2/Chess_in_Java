package chessboardElements.pieces;

public enum ChessPieceType {
    // Czarne figury

    KING('♔'),
    QUEEN('♕'),
    ROOK('♖'),
    BISHOP('♗'),
    KNIGHT('♘'),
    PAWN('♙');



    /*
    // Białe figury
    KING('♚'),
    QUEEN('♛'),
    ROOK('♜'),
    BISHOP('♝'),
    KNIGHT('♞'),
    PAWN('♟');

     */



    // Pole przechowujące symbol figury
    private final char symbol;
    // Konstruktor enum dla symbolu
    ChessPieceType(char symbol) {
        this.symbol = symbol;
    }
    // Getter zwracający symbol figury
    public char getSymbol() {
        return symbol;
    }
}
