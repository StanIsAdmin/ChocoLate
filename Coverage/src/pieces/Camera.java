package pieces;

import org.chocosolver.solver.expression.discrete.relational.ReExpression;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

/**
 * Represents a camera, which menaces the pieces directly in front of it.
 * A camera can face the north, south, east or west.
 */
public class Camera extends Piece {
    protected IntVar _direction;
    
    @Override
    public void setCoordinates(IntVar xCoordinate, IntVar yCoordinate, BoolVar onBoard) {
        super.setCoordinates(xCoordinate, yCoordinate, onBoard);
        _direction = xCoordinate.getModel().intVar(0, 3);
    }
    
    @Override
    public String getPieceName() {
        if (_direction == null) {
            return "O";
        } else {
            switch (_direction.getValue()) {
                case 1:
                    return "N";
                case 2:
                    return "S";
                case 3:
                    return "E";
                case 4:
                    return "W";
                default:
                    return "?";
            }
        }
    }
    
    @Override
    public ReExpression menaces(Positioned target) {
        return super.menaces(target).and(
            ((_direction.eq(1)).and(getX().eq(target.getX())).and(getY().gt(target.getY())))
            .or((_direction.eq(2)).and(getX().eq(target.getX()).and(getY().lt(target.getY()))))
            .or((_direction.eq(3)).and(getY().eq(target.getY()).and(getX().lt(target.getX()))))
            .or((_direction.eq(4)).and(getY().eq(target.getY()).and(getX().gt(target.getX()))))
        );
    }
}

