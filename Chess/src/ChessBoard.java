
/**
 * Represents a chess board with chess pieces on it.
 */
public class ChessBoard {
    private final int _size;
    String[][] _board;

    public ChessBoard(int size) {
        this._size = size;
        _board = new String[_size][_size];
    }
    
    public void addPiece(String pieceName, int xCoordinate, int yCoordinate) {
        _board[xCoordinate][yCoordinate] = pieceName;
    }
    
    @Override
    public String toString() {
        String result = "";
        for (String[] boardLine : _board) {
            for (String boardPiece : boardLine) {
                if (boardPiece == null) {
                    result += " ";
                } else {
                    result += boardPiece;
                }
                result += " | ";
            }
            result += "\n";
        }
        return result;
    }
}
