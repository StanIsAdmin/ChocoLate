package independence;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

/**
 * Represents a chess game piece, with a name and a board position (x, y).
 */
public abstract class Piece {
    
    private final String _name;
    protected final Model _solverModel;
    protected final int _boardSize;
    protected IntVar _xCoordinate;
    protected IntVar _yCoordinate;

    public Piece(String name, Model solverModel, int boardSize) {
        this._name = name;
        this._solverModel = solverModel;
        this._boardSize = boardSize;
        setCoordinatesDomain(boardSize);
    }
    
    public void setCoordinatesDomain(int boardSize) {
        _xCoordinate = _solverModel.intVar(_name + "_x", 1, boardSize);
        _yCoordinate = _solverModel.intVar(_name + "_y", 1, boardSize);
    }
    
    public IntVar getXCoordinate() {
        return _xCoordinate;
    }
    
    public IntVar getYCoordinate() {
        return _yCoordinate;
    }
    
    public abstract void addDoesNotMenaceConstraint(Piece other);
}
