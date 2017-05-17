import parser.Parser;
import pieces.Bishop;
import pieces.Knight;
import pieces.Rook;
import problems.AbstractCoverageProblem;
import problems.DominationProblem;
import problems.IndependenceProblem;


public class Main {
    /* command-line arguments parser */
    private static Parser _parser;
    
    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) {
        _parser = new Parser();
        _parser.parse(args);
        start();
    }
    
    private static void start() {
        AbstractCoverageProblem prob;
        int boardSizeX = _parser.getBoardSizeX();
        int boardSizeY = _parser.getBoardSizeY();
        
        if (_parser.problemIsDomination()) {
            prob = new DominationProblem(boardSizeX, boardSizeY);
        } else {
            prob = new IndependenceProblem(boardSizeX, boardSizeY);
        }
            
        solveProblem(prob);
    }
    
    private static void solveProblem(AbstractCoverageProblem prob) {
        // add required pieces
        for (int i=0; i<_parser.getRookCount(); i++) {
            prob.addPiece(new Rook());
        }
        for (int i=0; i<_parser.getBishopCount(); i++) {
            prob.addPiece(new Bishop());
        }
        for (int i=0; i<_parser.getKnightCount(); i++) {
            prob.addPiece(new Knight());
        }
        
        //Solve with objective
        if (_parser.objectiveIsMinimize()) {
            prob.solveMinimum();
        } else {
            prob.solve();
        }
        System.out.print(prob.getSolutionAsString().replace("-", "*"));
    }
}
