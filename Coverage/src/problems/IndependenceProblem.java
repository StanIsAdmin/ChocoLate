package problems;


import pieces.Piece;


/**
 * Represents an independence coverage problem.
 * An independence problem aims at covering the board with pieces so that
 * no piece menaces another.
 */
public class IndependenceProblem extends AbstractCoverageProblem {

    public IndependenceProblem(int boardSizeX, int boardSizeY) {
        super(boardSizeX, boardSizeY);
    }
    
    @Override
    protected void setConstraints() {
        super.setConstraints();
        for (Piece pieceA : _boardPieces) {
            for (Piece pieceB : _boardPieces) {
                if (pieceA != pieceB) {
                    pieceA.isOnBoard()
                        .and(pieceB.isOnBoard())
                        .and(pieceA.menaces(pieceB)).not().post();
                }
            }
        }
    }
}
