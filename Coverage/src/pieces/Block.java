package pieces;

import org.chocosolver.solver.expression.discrete.relational.ReExpression;

/**
 * Represents a simple block, which occupies its position but does not menace
 * other positions.
 */
public class Block extends Piece {
    @Override
    public String getName() {
        return "*";
    }
    
    @Override
    public ReExpression menaces(Positioned target) {
        return getX().ne(getX()); //Always false
    }
}
