package pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creates Piece instances from their names.
 */
public class PieceLoader {
    private static final ArrayList<Class<? extends Piece>> PIECE_CLASSES;
    private static final Map<String, Class<? extends Piece>> NAMES_TO_PIECES;
    static
    {
        PIECE_CLASSES = new ArrayList(Arrays.asList(
            Bishop.class,
            Block.class,
            CameraEast.class,
            CameraNorth.class,
            CameraSouth.class,
            CameraWest.class,
            Knight.class,
            Rook.class
        ));
        
        NAMES_TO_PIECES = new HashMap();
        for (Class<? extends Piece> c : PIECE_CLASSES) {
            String pieceName;
            try {
                pieceName = c.newInstance().getPieceName();
                if (NAMES_TO_PIECES.putIfAbsent(pieceName, c) != null) {
                    Logger.getLogger(PieceLoader.class.getName()).log(Level.WARNING, 
                            "Piece classes return same value for getName()");
                }
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(PieceLoader.class.getName()).log(Level.SEVERE, ex.toString());
            }
        }
    }
    
    public static Piece getPieceFromName(String name) {
        Class<? extends Piece> pieceClass = NAMES_TO_PIECES.get(name);
        try {
            return pieceClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(PieceLoader.class.getName()).log(Level.SEVERE, ex.toString());
        }
        return null;
    }

}
