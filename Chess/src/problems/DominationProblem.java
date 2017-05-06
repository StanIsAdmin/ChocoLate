package problems;

import java.util.ArrayList;
import org.chocosolver.solver.constraints.Constraint;
import pieces.Position;
import pieces.Piece;

/**
 * Represents a chess domination problem.
 */
public class DominationProblem extends ChessProblem {

    public DominationProblem(int boardSize, int rooks, int bishops, int knights) {
        super(boardSize, rooks, bishops, knights);
    }
    
    @Override
    protected void setConstraints() {
        super.setConstraints();
        for (Position position : _boardPositions) {
            ArrayList<Constraint> allMenaces = new ArrayList<>();
            for (Piece piece : _chessPieces) {
                allMenaces.add(piece.menaces(position).or(piece.hasSamePosition(position)).decompose());
            }
            _solverModel.or(allMenaces.toArray(new Constraint[allMenaces.size()])).post();
        }
    }
    
}
