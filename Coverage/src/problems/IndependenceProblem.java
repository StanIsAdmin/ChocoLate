package problems;


import pieces.Piece;


/**
 * Represents an independence coverage problem.
 * An independence problem aims at covering the board with pieces so that
 * no piece menaces another.
 */
public class IndependenceProblem extends AbstractCoverageProblem {

    public IndependenceProblem(int boardSize) {
        super(boardSize);
    }
    
    @Override
    protected void setConstraints() {
        super.setConstraints();
        for (Piece pieceA : _boardPieces) {
            for (Piece pieceB : _boardPieces) {
                if (pieceA != pieceB) {
                    pieceA.menaces(pieceB).not().post();
                }
            }
        }
    }
}
