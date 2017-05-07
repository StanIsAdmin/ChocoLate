package problems;


import pieces.Rook;
import pieces.Bishop;
import pieces.Knight;
import pieces.Piece;
import java.util.ArrayList;
import java.util.List;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import pieces.Position;


/**
 * Represents an abstract coverage problem.
 * A coverage problem aims at covering a set of positions or "board" with
 * pieces of different types, while respecting certain constraints.
 */
public abstract class AbstractCoverageProblem {
    /*The chocosolver model that allows us to solve the problem.*/
    protected Model _solverModel;
    private Solution _solution;
    
    /*The size of the chess board*/
    protected int _boardSize;
    
    /*The chess pieces on the board game*/
    protected List<Piece> _chessPieces = new ArrayList();
    
    /*The board positions*/
    protected List<Position> _boardPositions = new ArrayList();
    
    /**
     * Creates an abstract chess problem.
     * @param boardSize the size of the chess board
     * @param rooks the number of rooks on the chess board
     * @param bishops the number of bishops on the chess board
     * @param knights the number of knights on the chess board
     */
    public AbstractCoverageProblem(int boardSize, int rooks, int bishops, int knights) {
        _boardSize = boardSize;
        _solverModel = new Model();
        initChessPieces(rooks, bishops, knights);
        initBoardPositions();
        setConstraints();
    }
    
    /**
     * Initializes the board's pieces.
     * @param rooks the number of rooks to create
     * @param bishops the number of bishops to create
     * @param knights the number of knights to create
     */
    private void initChessPieces(int rooks, int bishops, int knights) {
        for (int i=0; i<rooks; i++) {
            _chessPieces.add(new Rook(_solverModel, _boardSize));
        }
        for (int i=0; i<bishops; i++) {
            _chessPieces.add(new Bishop(_solverModel, _boardSize));
        }
        for (int i=0; i<knights; i++) {
            _chessPieces.add(new Knight(_solverModel, _boardSize));
        }
    }
    
    private void initBoardPositions() {
        for (int i=0; i<_boardSize; i++) {
            for (int j=0; j<_boardSize; j++) {
                _boardPositions.add(new Position(_solverModel, i, j));
            }
        }
    }
    
    protected void setConstraints() {
        for (Piece pieceA : _chessPieces) {
            for (Piece pieceB : _chessPieces) {
                if (pieceA != pieceB) {
                    pieceA.occupies(pieceB).not().post();
                }
            }
        }
    }
    
    public void solve() {
        _solution = _solverModel.getSolver().findSolution();
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
        for (Piece piece : _chessPieces) {
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
