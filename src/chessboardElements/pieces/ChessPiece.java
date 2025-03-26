package chessboardElements.pieces;

public enum ChessPiece {
    // Czarne figury
    BLACK_KING('♔'),
    BLACK_QUEEN('♕'),
    BLACK_ROOK('♖'),
    BLACK_BISHOP('♗'),
    BLACK_KNIGHT('♘'),
    BLACK_PAWN('♙'),

    // Białe figury
    WHITE_KING('♚'),
    WHITE_QUEEN('♛'),
    WHITE_ROOK('♜'),
    WHITE_BISHOP('♝'),
    WHITE_KNIGHT('♞'),
    WHITE_PAWN('♟');



    // Pole przechowujące symbol figury
    private final char symbol;
    // Konstruktor enum dla symbolu
    ChessPiece(char symbol) {
        this.symbol = symbol;
    }
    // Getter zwracający symbol figury
    public char getSymbol() {
        return symbol;
    }
}
