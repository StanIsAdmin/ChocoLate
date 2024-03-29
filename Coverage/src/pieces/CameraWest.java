package pieces;

import org.chocosolver.solver.expression.discrete.relational.ReExpression;

/**
 * Represents a north-facing camera, which menaces same-column upper positions.
 */
public class CameraWest extends Piece {
    @Override
    public String getPieceName() {
        return "W";
    }
    
    @Override
    public ReExpression menaces(Positioned target) {
        return super.menaces(target)
            .and(getY().eq(target.getY()).and(getX().gt(target.getX())));
    }
}
