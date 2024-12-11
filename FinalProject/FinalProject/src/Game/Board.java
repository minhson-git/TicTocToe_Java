package Game;

public class Board {

    private final int width;
    private final Mark[][] board;

    public Board(int width) {
        this.width = width;
        board = new Mark[width][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = Mark.BLANK;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public boolean isTileMarked(int row, int col) {
        return board[row][col] != Mark.BLANK;
    }

    public void setMarkAt(int row, int col, Mark mark) {
        board[row][col] = mark;
    }

    public Mark getMarkAt(int row, int col) {
        return board[row][col];
    }

    public boolean anyMovesAvailable() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == Mark.BLANK) {
                    return true;
                }
            }
        }
        return false;
    }

    public enum Mark {
        X, O, BLANK
    }
}
