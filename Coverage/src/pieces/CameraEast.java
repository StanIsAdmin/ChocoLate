package pieces;

import org.chocosolver.solver.expression.discrete.relational.ReExpression;

/**
 * Represents an east-facing camera, which menaces same-column upper positions.
 */
public class CameraEast extends Piece {
    @Override
    public String getName() {
        return "E";
    }
    
    @Override
    public ReExpression menaces(Positioned target) {
        return super.menaces(target)
            .and(getY().eq(target.getY()).and(getX().lt(target.getX())));
    }
}
