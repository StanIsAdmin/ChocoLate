package problems;


import java.util.ArrayList;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;
import pieces.Position;
import pieces.Piece;


/**
 * Represents a domination coverage problem.
 * A domination problem aims at covering the board with pieces so that
 * all positions of the board are either occupied or menaced by at least one piece.
 */
public class DominationProblem extends AbstractCoverageProblem {

    public DominationProblem(int boardSize) {
        super(boardSize);
    }
    
    @Override
    protected void setConstraints() {
        super.setConstraints();
        for (Position position : _boardPositions) {
            ArrayList<ReExpression> anyMenaces = new ArrayList<>();
            for (Piece piece : _boardPieces) {
                anyMenaces.add(piece.isOnBoard().and(piece.menaces(position, _boardPieces).or(piece.occupies(position))));
            }
            anyMenaces.get(0).or(anyMenaces.toArray(new ReExpression[anyMenaces.size()])).post();
        }
    }
    
}
