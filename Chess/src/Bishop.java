

import org.chocosolver.solver.Model;

/**
 * Represents a bishop piece of a chess game.
 * @extends Piece
 */
public class Bishop extends Piece {
    
    public Bishop(String name, Model solverModel, int boardSize) {
        super(name, solverModel, boardSize);
    }
    
    @Override
    public void addDoesNotMenaceConstraint(Piece other) {
        _xCoordinate.dist(other.getXCoordinate()).ne(_yCoordinate.dist(other.getYCoordinate())).post();
    }
}
