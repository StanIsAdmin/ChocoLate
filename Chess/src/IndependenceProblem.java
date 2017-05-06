
/**
 * Represents an independence chess problem.
 */
public class IndependenceProblem extends ChessProblem {
    
    /**
     * Creates an independence chess problem.
     * @param boardSize the size of the chess board
     * @param rooks the number of rooks on the chess board
     * @param bishops the number of bishops on the chess board
     * @param knights the number of knights on the chess board
     */
    public IndependenceProblem(int boardSize, int rooks, int bishops, int knights) {
        super(boardSize, rooks, bishops, knights);
    }
    
    @Override
    protected void setConstraints() {
        for (Piece pieceA : _chessPieces) {
            for (Piece pieceB : _chessPieces) {
                if (pieceA != pieceB) {
                    pieceA.addNotInSamePlaceConstraint(pieceB);
                    pieceA.addDoesNotMenaceConstraint(pieceB);
                }
            }
        }
    }
}
