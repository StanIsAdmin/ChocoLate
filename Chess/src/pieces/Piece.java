package pieces;


import java.util.ArrayList;
import java.util.List;
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
    
    public ReExpression occupies(Positioned other) {
        return getX().eq(other.getX()).and(getY().eq(other.getY()));
    }
    
    public abstract ReExpression menaces(Positioned target);
    
    public ReExpression menaces(Positioned target, List<Piece> others) {
        ArrayList<ReExpression> anyBlocks = new ArrayList();
        for (Piece other : others) {
            anyBlocks.add(isBlocked(target, other).not());
        }
        return menaces(target).and(anyBlocks.toArray(new ReExpression[anyBlocks.size()]));
    }
    
    private ReExpression isBlocked(Positioned target, Piece other) {
        return menaces(other).and(
            other.getX().lt(getX().max(target.getX())).and(
            other.getX().gt(getX().min(target.getX()))).and(
            other.getY().lt(getY().max(target.getY()))).and(
            other.getY().gt(getY().min(target.getY())))
        );
    }
}
