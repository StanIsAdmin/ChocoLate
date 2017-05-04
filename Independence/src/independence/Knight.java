package independence;

import org.chocosolver.solver.Model;

/**
 * Represents a knight piece of a chess game.
 * @extends Piece
 */
public class Knight extends Piece {
    
    public Knight(String name, Model solverModel, int boardSize) {
        super(name, solverModel, boardSize);
    }
    
    @Override
    public void addDoesNotMenaceConstraint(Piece other) {
        _xCoordinate.dist(other.getXCoordinate()).eq(2).and(_yCoordinate.dist(other.getYCoordinate()).eq(1)).not().post();
        _xCoordinate.dist(other.getXCoordinate()).eq(1).and(_yCoordinate.dist(other.getYCoordinate()).eq(2)).not().post();
    }
}
