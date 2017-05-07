package pieces;


import java.util.List;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;
import org.chocosolver.solver.variables.IntVar;


/**
 * Represents a chess game piece, with a name and a board position (x, y).
 */
public abstract class Piece implements Positioned {
    protected IntVar _xCoordinate;
    protected IntVar _yCoordinate;
    
    public void setCoordinates(IntVar xCoordinate, IntVar yCoordinate) {
        _xCoordinate = xCoordinate;
        _yCoordinate = yCoordinate;
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
    
    /**
     * Detects when this piece is in the same positin as other.
     * @param other the positioned object whose position is compared
     * @return ReExpression that is true when this and other have same positions
     */
    public ReExpression occupies(Positioned other) {
        return getX().eq(other.getX()).and(getY().eq(other.getY()));
    }
    
    /**
     * Detects when target is menaced by this, without collision detection.
     * @param target the Positioned item to check
     * @return ReExpression that is true when this piece menaces target
     */
    public abstract ReExpression menaces(Positioned target);
    
    /**
     * Same as previous, but with collision detection
     * @param target the Positioned item to check
     * @param obstacles the Pieces that may be blocking the target
     * @return ReExpression that is true when this menaces target, without
     * any piece from others in the way
     */
    public ReExpression menaces(Positioned target, List<Piece> obstacles) {
        ReExpression[] notBlocked = new ReExpression[obstacles.size()];
        int i = 0;
        for (Piece obstacle : obstacles) {
            notBlocked[i] = isBlocked(target, obstacle).not();
            i++;
        }
        return menaces(target).and(notBlocked);
    }
    
    /**
     * Detects when a menaced target is blocked by obstacle.
     * @param target the target that we are checking
     * @param obstacle the piece that may be blocking our target
     * @return ReExpression that is true when obstacle blocks target
     */
    private ReExpression isBlocked(Positioned target, Piece obstacle) {
        return menaces(obstacle).and(menaces(target).and(
            obstacle.getX().lt(getX().max(target.getX())).and(
            obstacle.getX().gt(getX().min(target.getX()))).and(
            obstacle.getY().lt(getY().max(target.getY()))).and(
            obstacle.getY().gt(getY().min(target.getY())))
        ));
    }
}
