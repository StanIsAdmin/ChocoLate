package problems;


import pieces.Piece;
import java.util.ArrayList;
import java.util.List;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import parser.FileParser;
import pieces.PieceLoader;
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
    private boolean _minimize = false;
    
    /*Board positions*/
    protected int _boardSizeX, _boardSizeY;
    protected List<Position> _boardPositions = new ArrayList();
    
    /*Pieces on the board*/
    protected List<Piece> _boardPieces = new ArrayList();
    private int _boardVoidPositions;
    IntVar _boardPiecesCount;
    
    public AbstractCoverageProblem(String filePath) {
        FileParser fileParser = new FileParser(filePath);
        initInstance(fileParser.getBoardSizeX(), fileParser.getBoardSizeY());
        fillPieces(fileParser);
    }
    
    public AbstractCoverageProblem(int boardSizeX, int boardSizeY) {
        initInstance(boardSizeX, boardSizeY);
    }
    
    private void fillPieces(FileParser fileParser) {
        for (int x=0; x<_boardSizeX; x++) {
            for (int y=0; y<_boardSizeY; y++) {
                if (! fileParser.pieceIsVoidAt(x, y)) {
                    String pieceName = fileParser.getPieceNameAt(x, y);
                    Piece piece = PieceLoader.getPieceFromName(pieceName);
                    addPiece(piece, x, y);
                }
            }
        }
    }
    
    private void initInstance(int boardSizeX, int boardSizeY) {
        _model = new Model();
        _boardSizeX = boardSizeX;
        _boardSizeY = boardSizeY;
        _boardVoidPositions = boardSizeX * boardSizeY;
        initBoardPositions();
    }
    
    private void initBoardPositions() {
        for (int x=0; x<_boardSizeX; x++) {
            for (int y=0; y<_boardSizeY; y++) {
                _boardPositions.add(new Position(_model, x, y));
            }
        }
    }

    public void addPiece(Piece piece) {
        addPiece(piece, 0, _boardSizeX-1, 0, _boardSizeY-1, false);
    }
    
    public void addPiece(Piece piece, int positionX, int positionY) {
        addPiece(piece, positionX, positionX, positionY, positionY, true);
        _boardVoidPositions -= 1;
    }
    
    public void addPiece(Piece piece, int minX, int maxX, int minY, int maxY, boolean forceOnBoard) {
        IntVar xCoordinate = _model.intVar(minX, maxX);
        IntVar yCoordinate = _model.intVar(minY, maxY);
        BoolVar onBoard = forceOnBoard ? _model.boolVar(true) : _model.boolVar();
        piece.setCoordinates(xCoordinate, yCoordinate, onBoard);
        _boardPieces.add(piece);
    }
    
    public void addMaxPieces(String ... pieceNames) {
        for (String name : pieceNames) {
            for (int i=0; i<_boardVoidPositions; i++) {
                addPiece(PieceLoader.getPieceFromName(name));
            }
        }
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
        
        _boardPiecesCount = _model.intVar(0, _boardPieces.size());
        onBoardFirst.add(onBoard).eq(_boardPiecesCount).post();
        //_model.setObjective(Model.MINIMIZE, _boardPiecesCount);
    }
    
    public void solve() {
        setConstraints();
        enforceAllPieces();
        //_solved = _model.getSolver().solve();
        _solved = _model.getSolver().findSolution() != null;
    }
    
    public void solveMinimum() {
        _minimize = true;
        setConstraints();
        enforceMinimumPieces();
        //_solved = _model.getSolver().findOptimalSolution(_boardPiecesCount, Model.MINIMIZE) != null;
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
        String[][] board = new String[_boardSizeX][_boardSizeY];
        for (int x=0; x<_boardSizeX; x++) {
            for (int y=0; y<_boardSizeY; y++) {
                board[x][y] = voidChar;
            }
        }
        
        //Add pieces to board
        for (Piece piece : _boardPieces) {
            if (piece.getOnBoard().getValue() == 1) {
                board[piece.getX().getValue()][piece.getY().getValue()] = piece.getPieceName();
            }
        }
        
        //Create string from board
        String result = "";
        if (_minimize) {
            result += "" + _boardPiecesCount.getValue() + "\n";
        }
        for (Piece piece : _boardPieces) {
            result += piece.getPieceName() + " : " + piece.getX().getValue() + ", " + piece.getY().getValue() + "\n";
        }
        for (String[] boardLine : board) {
            result += String.join(separatorChar, boardLine);
            result += "\n";
        }
        return result;
    }
}
