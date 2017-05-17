package pieces;


import java.util.List;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;


/**
 * Represents a chess game piece, with a name and a board position (x, y).
 */
public abstract class Piece implements Positioned {
    private IntVar _xCoordinate;
    private IntVar _yCoordinate;
    private BoolVar _onBoard;
    
    public void setCoordinates(IntVar xCoordinate, IntVar yCoordinate, BoolVar onBoard) {
        _xCoordinate = xCoordinate;
        _yCoordinate = yCoordinate;
        _onBoard = onBoard;
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
    
    public BoolVar getOnBoard() {
        return _onBoard;
    }
    
    /**
     * Determines whether this piece is on the board.
     * A piece that's not on the board does not menace nor is menaced by
     * other pieces.
     * @return ReExpression that is true when the piece is on the board
     */
    public ReExpression isOnBoard() {
        return _onBoard.eq(1);
    }
    
    /**
     * Determines whether this piece is in the same (x, y) position as other.
     * @param other the positioned object whose position is compared to this
     * @return ReExpression that is true when this and other have same positions
     */
    public ReExpression occupies(Positioned other) {
        return getX().eq(other.getX()).and(getY().eq(other.getY()));
    }
    
    /**
     * Determines whether target is menaced by this.
     * Does not detect obstacles. The default behavior for a Piece
     * is to menace any position it does not occupy.
     * @param target the Positioned item to check
     * @return ReExpression that is true when this piece menaces target
     */
    public ReExpression menaces(Positioned target) {
        return occupies(target).not();
    }
    
    /**
     * Determines whether target is menaced by this, while no obstacle blocks it.
     * Calls isBlocked() in order to determine if any obstacle blocks the target.
     * @param target the Positioned item to check
     * @param obstacles the Pieces that may be blocking the target
     * @return ReExpression that is true when this menaces target, without
     * any obstacle blocking it
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
     * Determines whether a menaced target is blocked by obstacle.
     * The 'obstacle' blocks a menace if both 'obstacle' and 'target' are 
     * menaced by 'this', and 'obstacle' is located in the rectangular area 
     * between 'this' and 'target', bounds included.
     * @param target the target that we are checking
     * @param obstacle the piece that may be blocking our target
     * @return ReExpression that is true when obstacle blocks target for this
     */
    private ReExpression isBlocked(Positioned target, Piece obstacle) {
        return menaces(obstacle)
            .and(menaces(target))
            .and(obstacle.occupies(target).not())
            .and(obstacle.getX().le(getX().max(target.getX())))
            .and(obstacle.getX().ge(getX().min(target.getX())))
            .and(obstacle.getY().le(getY().max(target.getY())))
            .and(obstacle.getY().ge(getY().min(target.getY())));
    }
}
