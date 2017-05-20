package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import problems.AbstractCoverageProblem;

/**
 * Parses the input files provided when launching the program.
 * Use to create instances of coverage problems from descriptive files.
 */
public class FileParser {
    private final String[] _supportedPieces = {" ", "*"};
    private int _boardSizeX = 0;
    private int _boardSizeY = 0;
    private ArrayList<ArrayList<String>> _boardContent = new ArrayList();
    
    public FileParser(String inputFile) {
        try {
            scanFile(inputFile);
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find file " + inputFile);
        }
    }
    
    private void scanFile(String inputFile) throws FileNotFoundException {
        final Scanner s = new Scanner(new File(inputFile));
        while(s.hasNextLine()) {
            final String line = s.nextLine();
            processLine(line);
        }
    }
    
    private void processLine(String line) {
        ArrayList<String> newLine = new ArrayList();
        
        // first line
        if (_boardSizeY == 0) {
            String[] lineContent = line.split(" ");
            _boardSizeX = lineContent.length;
            Collections.addAll(newLine, lineContent);
        }
        
        // other lines
        else {
            for (int i=0; i<line.length(); i+= 2) {
                newLine.add(String.valueOf(line.charAt(i)));
            }
        }
        System.out.println(String.join("-", newLine));
        assert(_boardSizeX == newLine.size());
        _boardContent.add(newLine);
        _boardSizeY += 1;
    }
    
    public int getBoardSizeX() {
        return _boardSizeX;
    }
    
    public int getBoardSizeY() {
        return _boardSizeY;
    }
    
    public void fillProblemPieces(AbstractCoverageProblem problem) {
        
    }
}
