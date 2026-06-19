
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private boolean solvable;
    private SearchNode goalNode;

    private class SearchNode implements Comparable<SearchNode> {

        private final Board board;
        private final int moves;
        private final SearchNode previous;
        private final int priority;

        public SearchNode(Board board,
                          int moves,
                          SearchNode previous) {

            this.board = board;
            this.moves = moves;
            this.previous = previous;

            // cache Manhattan priority
            this.priority = moves + board.manhattan();
        }

        @Override
        public int compareTo(SearchNode that) {

            if (this.priority != that.priority)
                return this.priority - that.priority;

            return this.board.manhattan()
                   - that.board.manhattan();
        }
    }

    // find a solution to the initial board
    public Solver(Board initial) {

        if (initial == null)
            throw new IllegalArgumentException();

        MinPQ<SearchNode> pq =
                new MinPQ<>();

        MinPQ<SearchNode> twinPQ =
                new MinPQ<>();

        pq.insert(new SearchNode(initial,
                                 0,
                                 null));

        twinPQ.insert(new SearchNode(initial.twin(),
                                     0,
                                     null));

        while (true) {

            SearchNode node = step(pq);

            if (node != null) {
                solvable = true;
                goalNode = node;
                break;
            }

            SearchNode twinNode = step(twinPQ);

            if (twinNode != null) {
                solvable = false;
                goalNode = null;
                break;
            }
        }
    }

    private SearchNode step(MinPQ<SearchNode> pq) {

        SearchNode current = pq.delMin();

        if (current.board.isGoal())
            return current;

        for (Board neighbor : current.board.neighbors()) {

            // Critical Optimization:
            // do not revisit previous board

            if (current.previous != null &&
                neighbor.equals(current.previous.board))
                continue;

            pq.insert(
                new SearchNode(
                    neighbor,
                    current.moves + 1,
                    current
                )
            );
        }

        return null;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves
    public int moves() {

        if (!solvable)
            return -1;

        return goalNode.moves;
    }

    // sequence of boards in shortest solution
    public Iterable<Board> solution() {

        if (!solvable)
            return null;

        Stack<Board> solution =
                new Stack<>();

        SearchNode current = goalNode;

        while (current != null) {
            solution.push(current.board);
            current = current.previous;
        }

        return solution;
    }

    // test client
    public static void main(String[] args) {

        In in = new In(args[0]);

        int n = in.readInt();

        int[][] tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board initial =
                new Board(tiles);

        Solver solver =
                new Solver(initial);

        if (!solver.isSolvable()) {

            StdOut.println(
                    "No solution possible");
        }
        else {

            StdOut.println(
                    "Minimum number of moves = "
                    + solver.moves());

            for (Board board :
                    solver.solution()) {

                StdOut.println(board);
            }
        }
    }
}