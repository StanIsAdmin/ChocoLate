package problems;


import pieces.Piece;


/**
 * Represents an independence chess problem.
 */
public class IndependenceProblem extends CoverageProblem {
    
    public IndependenceProblem(int boardSize, int rooks, int bishops, int knights) {
        super(boardSize, rooks, bishops, knights);
    }
    
    @Override
    protected void setConstraints() {
        super.setConstraints();
        for (Piece pieceA : _chessPieces) {
            for (Piece pieceB : _chessPieces) {
                if (pieceA != pieceB) {
                    pieceA.menaces(pieceB).not().post();
                }
            }
        }
    }
}
