public class Minesweeper {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]); // rows
        int n = Integer.parseInt(args[1]); // columns
        int k = Integer.parseInt(args[2]); // mines

        boolean[][] mines = new boolean[m][n];

        // Place k mines randomly
        int placed = 0;
        while (placed < k) {
            int row = (int) (Math.random() * m);
            int col = (int) (Math.random() * n);

            if (!mines[row][col]) {
                mines[row][col] = true;
                placed++;
            }
        }

        // Print board with mine counts
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (mines[i][j]) {
                    System.out.print("* ");
                } else {
                    int count = 0;

                    for (int r = Math.max(0, i - 1);
                         r <= Math.min(m - 1, i + 1); r++) {

                        for (int c = Math.max(0, j - 1);
                             c <= Math.min(n - 1, j + 1); c++) {

                            if (mines[r][c]) count++;
                        }
                    }

                    System.out.print(count + " ");
                }
            }
            System.out.println();
        }
    }
}