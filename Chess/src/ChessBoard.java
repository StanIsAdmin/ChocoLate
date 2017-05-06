
/**
 * Represents a chess board with chess pieces on it.
 */
public class ChessBoard {
    private final int _size;
    String[][] _board;

    public ChessBoard(int size) {
        this._size = size;
        _board = new String[_size][_size];
        for (int i=0; i<_size; i++) {
            for (int j=0; j<_size; j++) {
                _board[i][j] = "*";
            }
        }
    }
    
    public void addPiece(String pieceName, int xCoordinate, int yCoordinate) {
        _board[xCoordinate][yCoordinate] = pieceName;
    }
    
    @Override
    public String toString() {
        String result = "";
        for (String[] boardLine : _board) {
            result += String.join(" ", boardLine);
            result += "\n";
        }
        return result;
    }
}
