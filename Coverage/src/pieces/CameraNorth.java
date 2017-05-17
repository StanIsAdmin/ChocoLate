package pieces;

import org.chocosolver.solver.expression.discrete.relational.ReExpression;

/**
 * Represents a north-facing camera, which menaces same-column upper positions.
 */
public class CameraNorth extends Piece {
    @Override
    public String getName() {
        return "N";
    }
    
    @Override
    public ReExpression menaces(Positioned target) {
        return super.menaces(target)
            .and(getX().eq(target.getX()).and(getY().gt(target.getY())));
    }
}
