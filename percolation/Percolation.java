import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final boolean[][] open;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufFull;
    private final int virtualTop;
    private final int virtualBottom;

    private int openSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }

        this.n = n;
        this.open = new boolean[n][n];
        this.openSites = 0;

        virtualTop = n * n;
        virtualBottom = n * n + 1;

        uf = new WeightedQuickUnionUF(n * n + 2);
        ufFull = new WeightedQuickUnionUF(n * n + 1);
    }

    private int index(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException(
                    "row and col must be between 1 and " + n);
        }
    }

    public void open(int row, int col) {
        validate(row, col);

        if (isOpen(row, col)) {
            return;
        }

        open[row - 1][col - 1] = true;
        openSites++;

        int current = index(row, col);

        if (row == 1) {
            uf.union(current, virtualTop);
            ufFull.union(current, virtualTop);
        }

        if (row == n) {
            uf.union(current, virtualBottom);
        }

        // up
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(current, index(row - 1, col));
            ufFull.union(current, index(row - 1, col));
        }

        // down
        if (row < n && isOpen(row + 1, col)) {
            uf.union(current, index(row + 1, col));
            ufFull.union(current, index(row + 1, col));
        }

        // left
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(current, index(row, col - 1));
            ufFull.union(current, index(row, col - 1));
        }

        // right
        if (col < n && isOpen(row, col + 1)) {
            uf.union(current, index(row, col + 1));
            ufFull.union(current, index(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return open[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);

        return isOpen(row, col)
                && ufFull.find(index(row, col))
                == ufFull.find(virtualTop);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return uf.find(virtualTop) == uf.find(virtualBottom);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(3);

        p.open(1, 2);
        p.open(2, 2);
        p.open(3, 2);

        System.out.println(p.percolates());
    }
}