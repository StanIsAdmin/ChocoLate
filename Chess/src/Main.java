import problems.DominationProblem;
import problems.IndependenceProblem;

public class Main {
    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) {
        IndependenceProblem ind = new IndependenceProblem(3,1,1,1);
        ind.solve();
        System.out.println(ind.getSolutionAsString());
        
        DominationProblem dom = new DominationProblem(3, 2, 1, 1);
        dom.solve();
        System.out.println(dom.getSolutionAsString());
        
        minimumKnightsDomination(4);
    }
    
    public static void minimumKnightsDomination(int boardSize) {
        String bestSolution = "";
        String prefix = String.valueOf(boardSize) + "\n";
        for (int knights=boardSize*boardSize; knights>0; knights--) {
            DominationProblem thisDom = new DominationProblem(boardSize, 0, 0, knights);
            thisDom.solve();
            if (thisDom.hasSolution()) {
                bestSolution = prefix + thisDom.getSolutionAsString();
            } else {
                break;
            }
        }
        System.out.println(bestSolution);
    }
    
}
