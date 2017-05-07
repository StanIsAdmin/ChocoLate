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
        return "F";
    }
    
    @Override
    public ReExpression menaces(Positioned target) {
        return getX().dist(target.getX()).eq(getY().dist(target.getY()));
    }
}
