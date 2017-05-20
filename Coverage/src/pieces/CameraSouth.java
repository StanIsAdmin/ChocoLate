package pieces;

import org.chocosolver.solver.expression.discrete.relational.ReExpression;

/**
 * Represents a south-facing camera, which menaces same-column upper positions.
 */
public class CameraSouth extends Piece {
    @Override
    public String getPieceName() {
        return "S";
    }
    
    @Override
    public ReExpression menaces(Positioned target) {
        return super.menaces(target)
            .and(getX().eq(target.getX()).and(getY().lt(target.getY())));
    }
}
