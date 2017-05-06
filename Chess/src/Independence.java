

import java.util.ArrayList;
import java.util.List;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;

/**
 * Represents an independence chess problem.
 */
public class Independence {
    /*The chocosolver model that allows us to solve the problem.*/
    private Model _solverModel;
    
    /*The size of the chess board*/
    private int _boardSize;
    
    /*The chess pieces on the board game*/
    private List<Piece> _chessPieces;
    
    /**
     * Creates an independence chess problem.
     * @param boardSize the size of the chess board
     * @param rooks the number of rooks on the chess board
     * @param bishops the number of bishops on the chess board
     * @param knights the number of knights on the chess board
     */
    public Independence(int boardSize, int rooks, int bishops, int knights) {
        _boardSize = boardSize;
        initSolverModel();
        initChessPieces(rooks, bishops, knights);
        setConstraints();
    }
        
    /**
     * Initializes the chocosolver model used to solve the problem.
     */
    private void initSolverModel() {
        _solverModel = new Model("Independence");
    }
    
    /**
     * Initializes the board's chess pieces.
     * @param rooks the number of rooks to create
     * @param bishops the number of bishops to create
     * @param knights the number of knights to create
     */
    private void initChessPieces(int rooks, int bishops, int knights) {
        _chessPieces = new ArrayList<>();
        for (int i=0; i<rooks; i++) {
            _chessPieces.add(new Rook("R" + i, _solverModel, _boardSize));
        }
        for (int i=0; i<bishops; i++) {
            _chessPieces.add(new Bishop("B" + i, _solverModel, _boardSize));
        }
        for (int i=0; i<knights; i++) {
            _chessPieces.add(new Knight("K" + i, _solverModel, _boardSize));
        }
    }
    
    private void setConstraints() {
        for (Piece pieceA : _chessPieces) {
            for (Piece pieceB : _chessPieces) {
                if (pieceA != pieceB) {
                    pieceA.addNotInSamePlaceConstraint(pieceB);
                    pieceA.addDoesNotMenaceConstraint(pieceB);
                }
            }
        }
    }   
    
    public String getSolution() {
        Solution solution = _solverModel.getSolver().findSolution();
        if (solution == null) {
            return "pas de solution";
        }
        
        ChessBoard board = new ChessBoard(_boardSize);
        for (Piece piece : _chessPieces) {
            board.addPiece(piece.getTypeName(), piece.getXCoordinate().getValue(), piece.getYCoordinate().getValue());
        }
        return board.toString();
    }
}
