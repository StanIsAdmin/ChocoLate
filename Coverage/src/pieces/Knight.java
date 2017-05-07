package pieces;


import org.chocosolver.solver.expression.discrete.relational.ReExpression;


/**
 * Represents a knight piece of a chess game.
 * @extends Piece
 */
public class Knight extends Piece {
    
    @Override
    public String getName() {
        return "C";
    }
    
    @Override
    public ReExpression menaces(Positioned target) {
        return getX().dist(target.getX()).eq(2).and(getY().dist(target.getY()).eq(1)).or(
            getX().dist(target.getX()).eq(1).and(getY().dist(target.getY()).eq(2)));
    }
}
