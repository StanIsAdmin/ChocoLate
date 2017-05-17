package problems;


import pieces.Piece;
import java.util.ArrayList;
import java.util.List;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import pieces.Position;


/**
 * Represents an abstract coverage problem.
 * A coverage problem aims at covering a set of positions or "board" with
 * pieces of different types, while respecting certain constraints.
 */
public class AbstractCoverageProblem {
    /*Choco Solver model and its solution*/
    protected Model _model;
    private boolean _solved;
    
    /*Board positions*/
    protected int _boardSize;
    protected List<Position> _boardPositions = new ArrayList();
    
    /*Pieces on the board*/
    protected List<Piece> _boardPieces = new ArrayList();
    
    public AbstractCoverageProblem(int boardSize) {
        _model = new Model();
        _boardSize = boardSize;
        initBoardPositions();
    }
    
    private void initBoardPositions() {
        for (int i=0; i<_boardSize; i++) {
            for (int j=0; j<_boardSize; j++) {
                _boardPositions.add(new Position(_model, i, j));
            }
        }
    }

    public void addPiece(Piece piece) {
        IntVar xCoordinate = _model.intVar(0, _boardSize-1);
        IntVar yCoordinate = _model.intVar(0, _boardSize-1);
        BoolVar onBoard = _model.boolVar();
        piece.setCoordinates(xCoordinate, yCoordinate, onBoard);
        _boardPieces.add(piece);
    }
    
    protected void setConstraints() {
        //No more than one piece per position
        for (Piece pieceA : _boardPieces) {
            for (Piece pieceB : _boardPieces) {
                if (pieceA != pieceB) {
                    pieceA.occupies(pieceB).not().post();
                }
            }
        }
    }
    
    private void enforceAllPieces() {
        //All pieces have to be used, _boardPiecesCount is fixed
        for (Piece piece : _boardPieces) {
            piece.isOnBoard().post();
        }
    }
    
    private void enforceMinimumPieces() {
        //Pieces may be left unused, _boardPiecesCount is the number of pieces on the board
        ReExpression[] onBoard = new ReExpression[_boardPieces.size()-1];
        ReExpression onBoardFirst = _boardPieces.get(0).isOnBoard();
        for (int i=0; i<_boardPieces.size()-1; i++) {
            onBoard[i] = _boardPieces.get(i+1).isOnBoard();
        }
        
        IntVar boardPiecesCount = _model.intVar(0, _boardPieces.size());
        onBoardFirst.add(onBoard).eq(boardPiecesCount).post();
        _model.setObjective(Model.MINIMIZE, boardPiecesCount);
    }
    
    public void solve() {
        setConstraints();
        enforceAllPieces();
        _solved = _model.getSolver().solve();
    }
    
    public void solveMinimum() {
        setConstraints();
        enforceMinimumPieces();
        _solved = false;
        while (_model.getSolver().solve()) {
            _solved = true;
        }
    }
    
    public boolean hasSolution() {
        return _solved;
    }
    
    public String getSolutionAsString() {
        if (! hasSolution()) {
            return "pas de solution\n";
        }
        
        String voidChar = "-";
        String separatorChar = " ";
        
        //Create empty board representation
        String[][] board = new String[_boardSize][_boardSize];
        for (int i=0; i<_boardSize; i++) {
            for (int j=0; j<_boardSize; j++) {
                board[i][j] = voidChar;
            }
        }
        
        //Add pieces to board
        for (Piece piece : _boardPieces) {
            if (piece.getOnBoard().getValue() == 1) {
                board[piece.getX().getValue()][piece.getY().getValue()] = piece.getName();
            }
        }
        
        //Create string from board
        String result = "";
        for (String[] boardLine : board) {
            result += String.join(separatorChar, boardLine);
            result += "\n";
        }
        return result;
    }
}
