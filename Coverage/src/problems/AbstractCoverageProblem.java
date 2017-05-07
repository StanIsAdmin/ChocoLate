package problems;


import pieces.Piece;
import java.util.ArrayList;
import java.util.List;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;
import pieces.Position;


/**
 * Represents an abstract coverage problem.
 * A coverage problem aims at covering a set of positions or "board" with
 * pieces of different types, while respecting certain constraints.
 */
public abstract class AbstractCoverageProblem {
    /*The chocosolver model that allows us to solve the problem.*/
    protected Model _model;
    private Solution _solution;
    
    /*The size of the board*/
    protected int _boardSize;
    
    /*The pieces on the board*/
    protected List<Piece> _boardPieces = new ArrayList();
    
    /*The board positions*/
    protected List<Position> _boardPositions = new ArrayList();
    
    public AbstractCoverageProblem(int boardSize) {
        _model = new Model();
        _boardSize = boardSize;
        initBoardPositions();
    }
    
    public Model getModel() {
        return _model;
    }
    
    public void addPiece(Piece piece) {
        IntVar xCoordinate = _model.intVar(0, _boardSize-1);
        IntVar yCoordinate = _model.intVar(0, _boardSize-1);
        piece.setCoordinates(xCoordinate, yCoordinate);
        _boardPieces.add(piece);
    }
    
    private void initBoardPositions() {
        for (int i=0; i<_boardSize; i++) {
            for (int j=0; j<_boardSize; j++) {
                _boardPositions.add(new Position(_model, i, j));
            }
        }
    }
    
    protected void setConstraints() {
        for (Piece pieceA : _boardPieces) {
            for (Piece pieceB : _boardPieces) {
                if (pieceA != pieceB) {
                    pieceA.occupies(pieceB).not().post();
                }
            }
        }
    }
    
    public void solve() {
        setConstraints();
        _solution = _model.getSolver().findSolution();
    }
    
    public boolean hasSolution() {
        return _solution != null;
    }
    
    public String getSolutionAsString() {
        if (! hasSolution()) {
            return "pas de solution";
        }
        
        //Create empty board representation
        String[][] board = new String[_boardSize][_boardSize];
        for (int i=0; i<_boardSize; i++) {
            for (int j=0; j<_boardSize; j++) {
                board[i][j] = "*";
            }
        }
        
        //Add pieces to board
        for (Piece piece : _boardPieces) {
            board[piece.getX().getValue()][piece.getY().getValue()] = piece.getName();
        }
        
        //Create string from board
        String result = "";
        for (String[] boardLine : board) {
            result += String.join(" ", boardLine);
            result += "\n";
        }
        return result;
    }
}
