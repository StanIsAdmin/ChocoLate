package problems;


import pieces.Piece;


/**
 * Represents an independence coverage problem.
 * An independence problem aims at covering the board with pieces so that
 * no piece menaces another.
 */
public class IndependenceProblem extends AbstractCoverageProblem {
    
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
