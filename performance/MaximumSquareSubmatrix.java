public class MaximumSquareSubmatrix {

    // Returns the size of the largest square submatrix of 1s.
    public static int size(int[][] a) {
        int rows = a.length;
        int cols = a[0].length;

        int[][] dp = new int[rows][cols];
        int maxSize = 0;

        for (int i = 0; i < rows; i++) {
            dp[i][0] = a[i][0];
            maxSize = Math.max(maxSize, dp[i][0]);
        }

        for (int j = 0; j < cols; j++) {
            dp[0][j] = a[0][j];
            maxSize = Math.max(maxSize, dp[0][j]);
        }

        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                if (a[i][j] == 1) {
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j],
                            Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                    maxSize = Math.max(maxSize, dp[i][j]);
                }
                else {
                    dp[i][j] = 0;
                }
            }
        }

        return maxSize;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                { 0, 1, 1, 0, 1 },
                { 1, 1, 1, 1, 0 },
                { 0, 1, 1, 1, 0 },
                { 1, 1, 1, 1, 0 },
                { 1, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 0 }
        };

        System.out.println(size(matrix));
    }
}