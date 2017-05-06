package pieces;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;
import org.chocosolver.solver.variables.IntVar;

/**
 * Represents a chess game piece, with a name and a board position (x, y).
 */
public abstract class Piece {
    protected IntVar _xCoordinate;
    protected IntVar _yCoordinate;

    public Piece(String name, Model solverModel, int boardSize) {
        _xCoordinate = solverModel.intVar(0, boardSize-1);
        _yCoordinate = solverModel.intVar(0, boardSize-1);
    }
    
    public IntVar getXCoordinate() {
        return _xCoordinate;
    }
    
    public IntVar getYCoordinate() {
        return _yCoordinate;
    }
    
    public abstract String getName();
    
    public ReExpression hasSamePosition(Piece other) {
        return _xCoordinate.eq(other._xCoordinate).and(_yCoordinate.eq(other._yCoordinate));
    }
    
    public abstract ReExpression menaces(Piece other);
}
