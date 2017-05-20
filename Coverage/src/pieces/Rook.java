package pieces;


import org.chocosolver.solver.expression.discrete.relational.ReExpression;


/**
 * Represents a rook piece of a chess game.
 * @extends Piece
 */
public class Rook extends Piece {
    
    @Override
    public String getPieceName() {
        return "T";
    }

    @Override
    public ReExpression menaces(Positioned target) {
        return super.menaces(target)
            .and(getX().eq(target.getX()).or(getY().eq(target.getY())));
    }
}
