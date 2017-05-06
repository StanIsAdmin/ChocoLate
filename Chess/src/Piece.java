

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
        _xCoordinate = _solverModel.intVar(_name + "_x", 0, boardSize-1);
        _yCoordinate = _solverModel.intVar(_name + "_y", 0, boardSize-1);
    }
    
    public IntVar getXCoordinate() {
        return _xCoordinate;
    }
    
    public IntVar getYCoordinate() {
        return _yCoordinate;
    }
    
    public String getTypeName() {
        return _name.substring(0, 1);
    }
    
    public void addNotInSamePlaceConstraint(Piece other) {
        _xCoordinate.eq(other._xCoordinate).and(_yCoordinate.eq(other._yCoordinate)).not().post();
    }
    
    public abstract void addDoesNotMenaceConstraint(Piece other);
}
