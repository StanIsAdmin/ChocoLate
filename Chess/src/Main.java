import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import problems.CoverageProblem;
import problems.DominationProblem;
import problems.IndependenceProblem;


public class Main {
    //CLI argument parsing
    private static Options options = new Options();
    private static CommandLine cmd;
    private static CommandLineParser parser = new DefaultParser();
    private static HelpFormatter formatter = new HelpFormatter();
    
    //parsed arguments
    static boolean independence, domination, surveillance;
    static boolean minimize;
    static int boardSize, rooks, bishops, knights;
    
    static class OptionComparator<T extends Option> implements Comparator<T> {
        private final List<String> OPTS_ORDER; //order of options
        
        public OptionComparator(List<String> optionsOrder) {
            OPTS_ORDER = optionsOrder;
        }
        
        @Override
        public int compare(T o1, T o2) {
            return OPTS_ORDER.indexOf(o1.getOpt()) - OPTS_ORDER.indexOf(o2.getOpt());
        }
    }
    
    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) {
        initOptions();
        parseArgs(args);
        start();
    }
    
    private static void initOptions() {
        options.addOption("d", false, "solve a domination problem");
        options.addOption("i", false, "solve an independence problem");
        options.addOption("s", false, "solve a surveillance problem");
        addPieceOption("n", "size", "size of the board (default is 8)");
        addPieceOption("t", "rooks", "number of rooks (default is 0)");
        addPieceOption("f", "bishops", "number of bishops (default is 0)");
        addPieceOption("c", "knights", "number of knights (default is 0)");
        options.addOption("m", "minimize", false,
                "minimize the total number of pieces\n"
                + " - numbers of pieces are used as maximums\n"
                + " - default values become number of board squares"
        );
        
        List<String> optionsOrder = Arrays.asList(
                "d", "i", "s", "n", "t", "f", "c", "m"
        );
        formatter.setOptionComparator(new OptionComparator(optionsOrder));
    }
    
    private static void addPieceOption(String optionName, String argName, String description) {
        options.addOption(
                OptionBuilder.withArgName(argName).hasArg(true).withDescription(description)
                        .withType(Integer.class).create(optionName)
        );
    }
    
    private static int getPieceOption(String optionName, int defaultValue) {
        int result = defaultValue;
        if (cmd.hasOption(optionName)) {
            try {
                result = Integer.parseInt(cmd.getOptionValue(optionName));
            } catch (NumberFormatException ex) {
                System.out.println(ex);
                quit();
            }
        }
        return result;
    }
    
    private static void parseArgs(String[] args) {
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            quit();
        }
        independence = cmd.hasOption("i");
        domination = cmd.hasOption("d");
        surveillance = cmd.hasOption("s");
        minimize = cmd.hasOption("min");
        boardSize = getPieceOption("n", 8);
        int defaultPieceCount = minimize ? boardSize*boardSize : 0;
        rooks = getPieceOption("t", defaultPieceCount);
        bishops = getPieceOption("f", defaultPieceCount);
        knights = getPieceOption("c", defaultPieceCount);
    }
    
    private static void start() {
        if (independence) {
            IndependenceProblem prob = new IndependenceProblem(boardSize, rooks, bishops, knights);
            solveProblem(prob);
        }
        if (domination) {
            DominationProblem prob = new DominationProblem(boardSize, rooks, bishops, knights);
            solveProblem(prob);
        }
        if (surveillance) {
            //TODO
        }
    }
    
    private static void solveProblem(CoverageProblem prob) {
        //TODO
        /*if (minimize) prob.setGoal("minimize");*/
        prob.solve();
        System.out.print(prob.getSolutionAsString());
    }
    
    private static void quit() {
        formatter.printHelp("board solver", options);
        System.exit(1);
    }
}
