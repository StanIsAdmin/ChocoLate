package problems;

import pieces.Piece;

/**
 * Represents a chess domination problem.
 */
public class DominationProblem extends ChessProblem {

    public DominationProblem(int boardSize, int rooks, int bishops, int knights) {
        super(boardSize, rooks, bishops, knights);
    }
    
    @Override
    protected void setConstraints() {
        for (Piece pieceA : _chessPieces) {
            for (Piece pieceB : _chessPieces) {
                if (pieceA != pieceB) {
                    //?
                }
            }
        }
    }
    
}
