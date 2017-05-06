package pieces;



import org.chocosolver.solver.Model;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;

/**
 * Represents a knight piece of a chess game.
 * @extends Piece
 */
public class Knight extends Piece {
    
    public Knight(String name, Model solverModel, int boardSize) {
        super(name, solverModel, boardSize);
    }
    
    @Override
    public ReExpression menaces(Piece other) {
        return _xCoordinate.dist(other.getXCoordinate()).eq(2).and(_yCoordinate.dist(other.getYCoordinate()).eq(1)).or(
            _xCoordinate.dist(other.getXCoordinate()).eq(1).and(_yCoordinate.dist(other.getYCoordinate()).eq(2)));
    }
}
