package problems;


import org.chocosolver.solver.expression.discrete.relational.ReExpression;
import pieces.Position;
import pieces.Piece;


/**
 * Represents a domination coverage problem.
 * A domination problem aims at covering the board with pieces so that
 * all positions of the board are either occupied or menaced by at least one piece.
 */
public class DominationProblem extends AbstractCoverageProblem {

    public DominationProblem(int boardSizeX, int boardSizeY) {
        super(boardSizeX, boardSizeY);
    }
    
    @Override
    protected void setConstraints() {
        super.setConstraints();
        for (Position position : _boardPositions) {
            ReExpression[] anyMenaces = new ReExpression[_boardPieces.size()];
            int i=0;
            for (Piece piece : _boardPieces) {
                anyMenaces[i] = piece.isOnBoard()
                    .and(piece.menaces(position, _boardPieces).or(piece.occupies(position)));
                i++;
            }
            anyMenaces[0].or(anyMenaces).post();
        }
    }
    
}
