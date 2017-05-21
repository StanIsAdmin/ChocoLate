package pieces;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creates Piece instances from their names.
 */
public class PieceLoader {
    
    public static Piece getPieceInstance(Class<? extends Piece> pieceClass) {
        try {
            return pieceClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(PieceLoader.class.getName()).log(Level.SEVERE, ex.toString());
        }
        return null;
    }

}
