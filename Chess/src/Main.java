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
    static String independenceOpt="i", dominationOpt="d", surveillanceOpt="s";
    static boolean minimize;
    static String minimizeOpt="m";
    static int boardSize, rooks, bishops, knights;
    static String boardSizeOpt="n", rooksOpt="t", bishopsOpt="f", knightsOpt="c";
    
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
        options.addOption(dominationOpt, false, "solve a domination problem");
        options.addOption(independenceOpt, false, "solve an independence problem");
        options.addOption(surveillanceOpt, false, "solve a surveillance problem");
        addPieceOption(boardSizeOpt, "size", "size of the board (default is 8)");
        addPieceOption(rooksOpt, "rooks", "number of rooks (default is 0)");
        addPieceOption(bishopsOpt, "bishops", "number of bishops (default is 0)");
        addPieceOption(knightsOpt, "knights", "number of knights (default is 0)");
        options.addOption(minimizeOpt, "minimize", false,
                "minimize the total number of pieces\n"
                + " - numbers of pieces are used as maximums\n"
                + " - default values become number of board squares"
        );
        
        List<String> optionsOrder = Arrays.asList(
                dominationOpt, independenceOpt, surveillanceOpt, boardSizeOpt,
                rooksOpt, bishopsOpt, knightsOpt, minimizeOpt
        );
        formatter.setOptionComparator(new OptionComparator(optionsOrder));
    }
    
    private static void addPieceOption(String optionName, String argName, String description) {
        options.addOption(
                OptionBuilder.withArgName(argName).hasArg(true).withDescription(description)
                        .withType(Integer.class).create(optionName)
        );
    }
    
    private static void parseArgs(String[] args) {
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            quit();
        }
        independence = cmd.hasOption(independenceOpt);
        domination = cmd.hasOption(dominationOpt);
        surveillance = cmd.hasOption(surveillanceOpt);
        minimize = cmd.hasOption(minimizeOpt);
        boardSize = getPieceOption(boardSizeOpt, 8);
        int defaultPieceCount = minimize ? boardSize*boardSize : 0;
        rooks = getPieceOption(rooksOpt, defaultPieceCount);
        bishops = getPieceOption(bishopsOpt, defaultPieceCount);
        knights = getPieceOption(knightsOpt, defaultPieceCount);
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
