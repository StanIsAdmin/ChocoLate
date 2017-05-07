package pieces;

import org.chocosolver.solver.variables.IntVar;

/**
 * Interface for all objects that have a position on the board.
 */
public interface Positioned {
    public abstract IntVar getX();
    public abstract IntVar getY();
}
