package pieces;



import org.chocosolver.solver.Model;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;

/**
 * Represents a knight piece of a chess game.
 * @extends Piece
 */
public class Knight extends Piece {
    
    public Knight(Model solverModel, int boardSize) {
        super(solverModel, boardSize);
    }
    
    @Override
    public String getName() {
        return "K";
    }
    
    @Override
    public ReExpression menaces(Positioned other) {
        return getX().dist(other.getX()).eq(2).and(getY().dist(other.getY()).eq(1)).or(
            getX().dist(other.getX()).eq(1).and(getY().dist(other.getY()).eq(2)));
    }
}
