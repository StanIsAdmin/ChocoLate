package pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates Piece instances from their names.
 */
public class PieceLoader {
    private static final ArrayList<Class<? extends Piece>> _pieceClasses;
    private static final Map<String, Class> _nameToPieces;
    static
    {
        _pieceClasses = new ArrayList(Arrays.asList(
            Bishop.class,
            Block.class,
            CameraEast.class,
            CameraNorth.class,
            CameraSouth.class,
            CameraWest.class,
            Knight.class,
            Rook.class
        ));
        
        _nameToPieces = new HashMap();
        for (Class c : _pieceClasses) {
            if (_nameToPieces.putIfAbsent(c.getName(), c) != null) {
                System.out.println("WARNING : Piece classes return same value for getName()");
            }
        }
    }
    
    public static Piece getPieceFromName(String name) {
        Class pieceClass = _nameToPieces.get(name);
        try {
            return (Piece) pieceClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            System.out.println("Could not create piece with name " + name);
        }
        return null;
    }

}
