package problems;


import pieces.Piece;
import java.util.ArrayList;
import java.util.List;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import parser.FileParser;
import pieces.Block;
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
    protected int _boardSizeX, _boardSizeY;
    protected List<Position> _boardPositions = new ArrayList();
    private int _boardVoidPositions;
    
    /*Pieces on the board*/
    protected List<Piece> _blockedPieces = new ArrayList();
    protected List<Piece> _freePieces = new ArrayList();
    protected List<Piece> _allPieces = new ArrayList();
    
    public AbstractCoverageProblem(String filePath) {
        FileParser fileParser = new FileParser(filePath);
        initInstance(fileParser.getBoardSizeX(), fileParser.getBoardSizeY());
        fillBoard(fileParser);
    }
    
    public AbstractCoverageProblem(int boardSizeX, int boardSizeY) {
        initInstance(boardSizeX, boardSizeY);
    }
    
    private void fillBoard(FileParser fileParser) {
        for (int x=0; x<_boardSizeX; x++) {
            for (int y=0; y<_boardSizeY; y++) {
                if (! fileParser.pieceIsVoidAt(x, y)) {
                    addBlockedPiece(new Block(), x, y);
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

    public void addFreePiece(Piece piece) {
        addPiece(piece, 0, _boardSizeX-1, 0, _boardSizeY-1, false);
        _freePieces.add(piece);
    }
    
    public void addBlockedPiece(Piece piece, int positionX, int positionY) {
        addPiece(piece, positionX, positionX, positionY, positionY, true);
        _boardVoidPositions -= 1;
        _boardPositions.removeIf(p -> p.x == positionX && p.y == positionY);
        _blockedPieces.add(piece);
    }
    
    private void addPiece(Piece piece, int minX, int maxX, int minY, int maxY, boolean forceOnBoard) {
        IntVar xCoordinate = _model.intVar(minX, maxX);
        IntVar yCoordinate = _model.intVar(minY, maxY);
        BoolVar onBoard = forceOnBoard ? _model.boolVar(true) : _model.boolVar();
        piece.setCoordinates(xCoordinate, yCoordinate, onBoard);
        _allPieces.add(piece);
    }
    
    public int getVoidPositionsCount() {
        return _boardVoidPositions;
    }
    
    protected void setConstraints() {
        //No more than one piece per position
        for (Piece pieceA : _freePieces) {
            for (Piece pieceB : _allPieces) {
                if (pieceA != pieceB) {
                    pieceA.occupies(pieceB).not().post();
                }
            }
        }
    }
    
    private void enforceAllPieces() {
        //All pieces have to be used, _boardPiecesCount is fixed
        for (Piece piece : _freePieces) {
            piece.isOnBoard().post();
        }
    }
    
    private void enforceMinimumPieces() {
        //Pieces may be left unused, _boardPiecesCount is the number of pieces on the board
        ReExpression[] onBoard = new ReExpression[_freePieces.size()-1];
        ReExpression onBoardFirst = _freePieces.get(0).isOnBoard();
        for (int i=0; i<_freePieces.size()-1; i++) {
            onBoard[i] = _freePieces.get(i+1).isOnBoard();
        }
        
        IntVar boardPiecesCount = _model.intVar(0, _freePieces.size());
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
        String[][] board = new String[_boardSizeX][_boardSizeY];
        for (int x=0; x<_boardSizeX; x++) {
            for (int y=0; y<_boardSizeY; y++) {
                board[x][y] = voidChar;
            }
        }
        
        //Add pieces to board
        for (Piece piece : _freePieces) {
            if (piece.getOnBoard().getValue() == 1) {
                board[piece.getX().getValue()][piece.getY().getValue()] = piece.getPieceName();
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
