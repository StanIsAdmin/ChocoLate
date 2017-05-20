package parser;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Parses the command-line arguments.
 */
public class ArgParser {
    // CLI argument parsing
    private static final Options options = new Options();
    private static final CommandLineParser parser = new DefaultParser();
    private static final HelpFormatter formatter = new HelpFormatter();
    private CommandLine cmd;
    
    // option identifiers
    static String independenceOpt="i", dominationOpt="d";
    static String minimizeOpt="m";
    static String boardSizeOpt="n", rooksOpt="t", bishopsOpt="f", knightsOpt="c";
    static String inputFileOpt="F";
    
    // default values
    int boardSizeDefault = 8;
    int pieceCountDefault = 0;
    
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
    
    public ArgParser() {
        // type of problem
        options.addOption(dominationOpt, false, "solve a domination problem");
        options.addOption(independenceOpt, false, "solve an independence problem");
        OptionGroup problemType = new OptionGroup();
        problemType.addOption(options.getOption(dominationOpt));
        problemType.addOption(options.getOption(independenceOpt));
        problemType.setRequired(true);
        options.addOptionGroup(problemType);
        
        // objective
        options.addOption(minimizeOpt, "minimize", false,
                "minimize the total number of pieces\n"
                + " - numbers of pieces are used as upper bounds"
        );
        
        // board parameters
        addPieceOption(boardSizeOpt, "size", "size of the board (default is 8)");
        options.addOption(inputFileOpt, true, "file representing the board");
        OptionGroup boardParameters = new OptionGroup();
        boardParameters.addOption(options.getOption(boardSizeOpt));
        boardParameters.addOption(options.getOption(inputFileOpt));
        boardParameters.setRequired(true);
        options.addOptionGroup(boardParameters);
        
        // piece parameters
        addPieceOption(rooksOpt, "rooks", "number of rooks (default is 0)");
        addPieceOption(bishopsOpt, "bishops", "number of bishops (default is 0)");
        addPieceOption(knightsOpt, "knights", "number of knights (default is 0)");
        
        // order of displayed arguments
        List<String> optionsOrder = Arrays.asList(
                dominationOpt, independenceOpt, boardSizeOpt,
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
    
    public void parse(String[] args) {
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            quit();
        }
    }
    
    private void quit() {
        formatter.printHelp("board solver", options);
        System.exit(1);
    }     
       
    private int getPieceOption(String optionName, int defaultValue) {
        int result = defaultValue;
        if (cmd.hasOption(optionName)) {
            try {
                result = Integer.parseInt(cmd.getOptionValue(optionName));
            } catch (NumberFormatException ex) {
                System.err.println(ex);
                quit();
            }
        }
        return result;
    }
    
    public boolean problemIsDomination() {
        return cmd.hasOption(dominationOpt);
    }
    
    public boolean problemIsIndependence() {
        return cmd.hasOption(independenceOpt);
    }
    
    public boolean objectiveIsMinimize() {
        return cmd.hasOption(minimizeOpt);
    }
    
    public int getBoardSizeX() {
        return getPieceOption(boardSizeOpt, boardSizeDefault);
    }
    
    public int getBoardSizeY() {
        return getPieceOption(boardSizeOpt, boardSizeDefault);
    }
    
    public int getRookCount() {
        return getPieceOption(rooksOpt, pieceCountDefault);
    }
    
    public int getBishopCount() {
        return getPieceOption(bishopsOpt, pieceCountDefault);
    }
    
    public int getKnightCount() {
        return getPieceOption(knightsOpt, pieceCountDefault);
    }
    
    public boolean hasInputFile() {
        return options.hasOption(inputFileOpt);
    }
    
    public String getInputFile() {
        if (hasInputFile()) {
            return cmd.getOptionValue(inputFileOpt);
        } else {
            return null;
        }
    }
}
