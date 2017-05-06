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
            ArrayList<Constraint> anyMenaces = new ArrayList<>();
            for (Piece piece : _chessPieces) {
                anyMenaces.add(piece.menaces(position, _chessPieces).or(piece.occupies(position)).decompose());
            }
            _solverModel.or(anyMenaces.toArray(new Constraint[anyMenaces.size()])).post();
        }
    }
    
}
