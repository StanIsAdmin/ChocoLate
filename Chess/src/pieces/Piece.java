package pieces;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;
import org.chocosolver.solver.variables.IntVar;

/**
 * Represents a chess game piece, with a name and a board position (x, y).
 */
public abstract class Piece implements Positioned {
    protected IntVar _xCoordinate;
    protected IntVar _yCoordinate;

    public Piece(Model solverModel, int boardSize) {
        _xCoordinate = solverModel.intVar(0, boardSize-1);
        _yCoordinate = solverModel.intVar(0, boardSize-1);
    }
    
    public abstract String getName();
    
    @Override
    public IntVar getX() {
        return _xCoordinate;
    }
    
    @Override
    public IntVar getY() {
        return _yCoordinate;
    }
    
    public ReExpression hasSamePosition(Positioned other) {
        return getX().eq(other.getX()).and(getY().eq(other.getY()));
    }
    
    public abstract ReExpression menaces(Positioned other);
}
