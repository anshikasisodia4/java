
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BoggleSolver {

    private static final int R = 26; // alphabet size

    // ---- Trie node ----
    private static class TrieNode {
        TrieNode[] next = new TrieNode[R];
        boolean isWord = false;
    }

    private final TrieNode root;

    /**
     * Initializes the data structure using the given array of strings as the dictionary.
     */
    public BoggleSolver(String[] dictionary) {
        root = new TrieNode();
        for (String word : dictionary) {
            insert(word);
        }
    }

    private void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'A';
            if (node.next[c] == null) {
                node.next[c] = new TrieNode();
            }
            node = node.next[c];
        }
        node.isWord = true;
    }

    /**
     * Returns the set of all valid words in the given Boggle board, as an Iterable.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> results = new HashSet<>();
        int rows = board.rows();
        int cols = board.cols();
        boolean[][] visited = new boolean[rows][cols];
        StringBuilder path = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dfs(board, visited, i, j, root, path, results);
            }
        }
        return results;
    }

    /**
     * DFS from cell (i, j), following the trie pointer `node` which represents
     * the trie position AFTER having consumed the letters already in `path`.
     */
    private void dfs(BoggleBoard board, boolean[][] visited, int i, int j,
                      TrieNode node, StringBuilder path, Set<String> results) {

        int rows = board.rows();
        int cols = board.cols();
        if (i < 0 || i >= rows || j < 0 || j >= cols || visited[i][j]) {
            return;
        }

        char letter = board.getLetter(i, j);

        // Determine the sequence of characters this die contributes.
        // 'Q' always represents "QU".
        int c1 = letter - 'A';
        TrieNode next1 = node.next[c1];
        if (next1 == null) {
            return; // no word in dictionary has this prefix -- prune
        }

        TrieNode afterFirst = next1;
        int pathLenAdded = 1;
        char secondChar = 0;

        if (letter == 'Q') {
            // must also match 'U'
            TrieNode next2 = next1.next['U' - 'A'];
            if (next2 == null) {
                return; // no word continues "...QU..."
            }
            afterFirst = next2;
            pathLenAdded = 2;
            secondChar = 'U';
        }

        // Mark visited, append letters
        visited[i][j] = true;
        path.append(letter);
        if (pathLenAdded == 2) {
            path.append(secondChar);
        }

        // Check if current path is a valid word (length >= 3 required by Boggle rules)
        if (afterFirst.isWord && path.length() >= 3) {
            results.add(path.toString());
        }

        // Explore neighbors (8 directions)
        for (int di = -1; di <= 1; di++) {
            for (int dj = -1; dj <= 1; dj++) {
                if (di == 0 && dj == 0) continue;
                dfs(board, visited, i + di, j + dj, afterFirst, path, results);
            }
        }

        // Backtrack
        path.setLength(path.length() - pathLenAdded);
        visited[i][j] = false;
    }

    /**
     * Returns the score of the given word if it is in the dictionary, zero otherwise.
     */
    public int scoreOf(String word) {
        if (word == null || word.length() < 3) {
            return 0;
        }
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'A';
            if (c < 0 || c >= R || node.next[c] == null) {
                return 0;
            }
            node = node.next[c];
        }
        if (!node.isWord) {
            return 0;
        }
        int length = word.length();
        if (length <= 2) return 0;
        else if (length <= 4) return 1;
        else if (length == 5) return 2;
        else if (length == 6) return 3;
        else if (length == 7) return 5;
        else return 11;
    }

    // ---- optional: simple test client ----
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java BoggleSolver dictionaryFile boardFile");
            return;
        }
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}