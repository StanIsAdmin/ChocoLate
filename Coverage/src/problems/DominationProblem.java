package problems;


import java.util.ArrayList;
import org.chocosolver.solver.constraints.Constraint;
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
            ArrayList<Constraint> anyMenaces = new ArrayList<>();
            for (Piece piece : _boardPieces) {
                anyMenaces.add(piece.menaces(position, _boardPieces).or(piece.occupies(position)).decompose());
            }
            _model.or(anyMenaces.toArray(new Constraint[anyMenaces.size()])).post();
        }
    }
    
}
