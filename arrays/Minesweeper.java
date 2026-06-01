public class Minesweeper {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);

        int[][] board = new int[m][n];

        // Place k mines randomly
        int minesPlaced = 0;
        while (minesPlaced < k) {
            int row = (int) (Math.random() * m);
            int col = (int) (Math.random() * n);

            if (board[row][col] != -1) {
                board[row][col] = -1;
                minesPlaced++;
            }
        }

        // Compute neighboring mine counts
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (board[i][j] == -1) {
                    continue;
                }

                int count = 0;

                for (int r = i - 1; r <= i + 1; r++) {
                    for (int c = j - 1; c <= j + 1; c++) {

                        if (r >= 0 && r < m && c >= 0 && c < n
                                && board[r][c] == -1) {
                            count++;
                        }
                    }
                }

                board[i][j] = count;
            }
        }

        // Print board
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (board[i][j] == -1) {
                    System.out.print("* ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}