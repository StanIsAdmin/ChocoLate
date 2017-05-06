package pieces;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;

/**
 * Represents a rook piece of a chess game.
 * @extends Piece
 */
public class Rook extends Piece {
    
    public Rook(Model solverModel, int boardSize) {
        super(solverModel, boardSize);
    }
    
    @Override
    public String getName() {
        return "R";
    }

    @Override
    public ReExpression menaces(Positioned other) {
        return getX().eq(other.getX()).or(getY().eq(other.getY()));
    }
}
