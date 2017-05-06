package problems;


import pieces.Piece;


/**
 * Represents an independence chess problem.
 */
public class IndependenceProblem extends ChessProblem {
    
    public IndependenceProblem(int boardSize, int rooks, int bishops, int knights) {
        super(boardSize, rooks, bishops, knights);
    }
    
    @Override
    protected void setConstraints() {
        for (Piece pieceA : _chessPieces) {
            for (Piece pieceB : _chessPieces) {
                if (pieceA != pieceB) {
                    pieceA.hasSamePosition(pieceB).not().post();
                    pieceA.menaces(pieceB).not().post();
                }
            }
        }
    }
}
