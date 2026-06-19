import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, n);
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(n).append("\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%2d ", tiles[i][j]));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                int expected = i * n + j + 1;

                if (tiles[i][j] != 0 &&
                    tiles[i][j] != expected) {
                    count++;
                }
            }
        }

        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                int value = tiles[row][col];

                if (value == 0)
                    continue;

                int goalRow = (value - 1) / n;
                int goalCol = (value - 1) % n;

                distance += Math.abs(row - goalRow)
                        + Math.abs(col - goalCol);
            }
        }

        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {

        if (this == y)
            return true;

        if (y == null)
            return false;

        if (getClass() != y.getClass())
            return false;

        Board that = (Board) y;

        if (this.n != that.n)
            return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        List<Board> neighbors = new ArrayList<>();

        int blankRow = -1;
        int blankCol = -1;

        // locate blank tile
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }

        int[][] directions = {
            {-1, 0}, // up
            { 1, 0}, // down
            { 0,-1}, // left
            { 0, 1}  // right
        };

        for (int[] dir : directions) {

            int newRow = blankRow + dir[0];
            int newCol = blankCol + dir[1];

            if (newRow >= 0 && newRow < n &&
                newCol >= 0 && newCol < n) {

                int[][] copy = copyTiles();

                swap(copy,
                     blankRow, blankCol,
                     newRow, newCol);

                neighbors.add(new Board(copy));
            }
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int[][] copy = copyTiles();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {

                if (copy[i][j] != 0 &&
                    copy[i][j + 1] != 0) {

                    swap(copy, i, j, i, j + 1);
                    return new Board(copy);
                }
            }
        }

        return null;
    }

    private int[][] copyTiles() {
        int[][] copy = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, copy[i], 0, n);
        }

        return copy;
    }

    private void swap(int[][] board,
                      int r1, int c1,
                      int r2, int c2) {

        int temp = board[r1][c1];
        board[r1][c1] = board[r2][c2];
        board[r2][c2] = temp;
    }

    // unit testing
    public static void main(String[] args) {

        int[][] tiles = {
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
        };

        Board board = new Board(tiles);

        System.out.println("Board:");
        System.out.println(board);

        System.out.println("Dimension = " + board.dimension());
        System.out.println("Hamming   = " + board.hamming());
        System.out.println("Manhattan = " + board.manhattan());
        System.out.println("Is Goal   = " + board.isGoal());

        System.out.println("\nNeighbors:");
        for (Board b : board.neighbors()) {
            System.out.println(b);
        }

        System.out.println("Twin:");
        System.out.println(board.twin());
    }
}