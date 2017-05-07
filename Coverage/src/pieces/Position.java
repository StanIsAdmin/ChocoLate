package pieces;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

/**
 * Represents a position on the chess board.
 */
public class Position implements Positioned {
    protected IntVar _xCoordinate;
    protected IntVar _yCoordinate;
    
    public Position(Model solverModel, int xCoordinate, int yCoordinate) {
        _xCoordinate = solverModel.intVar(xCoordinate);
        _yCoordinate = solverModel.intVar(yCoordinate);
    }

    @Override
    public IntVar getX() {
        return _xCoordinate;
    }

    @Override
    public IntVar getY() {
        return _yCoordinate;
    }
    
    
}
