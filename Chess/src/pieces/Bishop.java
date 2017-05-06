package pieces;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;

/**
 * Represents a bishop piece of a chess game.
 * @extends Piece
 */
public class Bishop extends Piece {
    
    public Bishop(String name, Model solverModel, int boardSize) {
        super(name, solverModel, boardSize);
    }
    
    @Override
    public ReExpression menaces(Piece other) {
        return _xCoordinate.dist(other.getXCoordinate()).eq(_yCoordinate.dist(other.getYCoordinate()));
    }
}
