import parser.ArgParser;
import pieces.Bishop;
import pieces.Camera;
import pieces.Knight;
import pieces.Rook;
import problems.AbstractCoverageProblem;
import problems.DominationProblem;
import problems.IndependenceProblem;


public class Main {
    /* command-line arguments parser */
    private static ArgParser _parser;
    
    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) {
        _parser = new ArgParser();
        _parser.parse(args);
        start();
    }
    
    private static void start() {
        AbstractCoverageProblem prob;
        if (_parser.hasInputFile()) {
            String inputFilePath = _parser.getInputFile();
            if (_parser.problemIsDomination()) {
                prob = new DominationProblem(inputFilePath);
            } else {
                prob = new IndependenceProblem(inputFilePath);
            }
            addMuseumPieces(prob);
            solveProblem(prob);
            System.out.println(prob.getSolutionAsString());
        } else {
            int boardSizeX = _parser.getBoardSizeX();
            int boardSizeY = _parser.getBoardSizeY();

            if (_parser.problemIsDomination()) {
                prob = new DominationProblem(boardSizeX, boardSizeY);
            } else {
                prob = new IndependenceProblem(boardSizeX, boardSizeY);
            }
            addCommandLinePieces(prob);
            solveProblem(prob);
            System.out.print(prob.getSolutionAsString().replace("-", "*"));
        }
        solveProblem(prob);
    }
    
    private static void addCommandLinePieces(AbstractCoverageProblem prob) {
        // add required pieces
        for (int i=0; i<_parser.getRookCount(); i++) {
            prob.addFreePiece(new Rook());
        }
        for (int i=0; i<_parser.getBishopCount(); i++) {
            prob.addFreePiece(new Bishop());
        }
        for (int i=0; i<_parser.getKnightCount(); i++) {
            prob.addFreePiece(new Knight());
        }
    }
    
    private static void addMuseumPieces(AbstractCoverageProblem prob) {
        int voidPositions = prob.getVoidPositionsCount();
        for (int i=0; i<voidPositions; i++) {
            prob.addFreePiece(new Camera());
        }
    }
    
    private static void solveProblem(AbstractCoverageProblem prob) {
        //Solve with objective
        if (_parser.objectiveIsMinimize()) {
            prob.solveMinimum();
        } else {
            prob.solve();
        }
    }
}
