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

    public DominationProblem(int boardSize, int rooks, int bishops, int knights) {
        super(boardSize, rooks, bishops, knights);
    }
    
    @Override
    protected void setConstraints() {
        super.setConstraints();
        for (Position position : _boardPositions) {
            ArrayList<Constraint> anyMenaces = new ArrayList<>();
            for (Piece piece : _chessPieces) {
                anyMenaces.add(piece.menaces(position, _chessPieces).or(piece.occupies(position)).decompose());
            }
            _solverModel.or(anyMenaces.toArray(new Constraint[anyMenaces.size()])).post();
        }
    }
    
}
