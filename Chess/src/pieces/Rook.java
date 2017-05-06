package pieces;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;

/**
 * Represents a rook piece of a chess game.
 * @extends Piece
 */
public class Rook extends Piece {
    
    public Rook(String name, Model solverModel, int boardSize) {
        super(name, solverModel, boardSize);
    }

    @Override
    public ReExpression menaces(Piece other) {
        return _xCoordinate.eq(other.getXCoordinate()).or(
            _yCoordinate.eq(other.getYCoordinate()));
    }
}
