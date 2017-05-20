package pieces;


import org.chocosolver.solver.expression.discrete.relational.ReExpression;


/**
 * Represents a bishop piece of a chess game.
 * @extends Piece
 */
public class Bishop extends Piece {
    
    @Override
    public String getPieceName() {
        return "F";
    }
    
    @Override
    public ReExpression menaces(Positioned target) {
        return super.menaces(target)
            .and(getX().dist(target.getX()).eq(getY().dist(target.getY())));
    }
}
