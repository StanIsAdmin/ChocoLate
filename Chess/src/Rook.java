

import org.chocosolver.solver.Model;

/**
 * Represents a rook piece of a chess game.
 * @extends Piece
 */
public class Rook extends Piece {
    
    public Rook(String name, Model solverModel, int boardSize) {
        super(name, solverModel, boardSize);
    }

    @Override
    public void addDoesNotMenaceConstraint(Piece other) {
        _xCoordinate.ne(other.getXCoordinate()).post();
        _yCoordinate.ne(other.getYCoordinate()).post();
    }
}
