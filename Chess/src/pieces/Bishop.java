package pieces;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;

/**
 * Represents a bishop piece of a chess game.
 * @extends Piece
 */
public class Bishop extends Piece {
    
    public Bishop(Model solverModel, int boardSize) {
        super(solverModel, boardSize);
    }
    
    @Override
    public String getName() {
        return "B";
    }
    
    @Override
    public ReExpression menaces(Positioned other) {
        return getX().dist(other.getX()).eq(getY().dist(other.getY()));
    }
}
