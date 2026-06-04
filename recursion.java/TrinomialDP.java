public class TrinomialDP {

    // Returns the trinomial coefficient T(n, k).
    public static long trinomial(int n, int k) {

        if (k < -n || k > n) {
            return 0;
        }

        long[][] dp = new long[n + 1][2 * n + 1];

        // T(0, 0) = 1
        dp[0][n] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = -i; j <= i; j++) {

                long left = 0;
                long middle = 0;
                long right = 0;

                if (j - 1 >= -(i - 1)) {
                    left = dp[i - 1][n + j - 1];
                }

                if (j >= -(i - 1) && j <= (i - 1)) {
                    middle = dp[i - 1][n + j];
                }

                if (j + 1 <= (i - 1)) {
                    right = dp[i - 1][n + j + 1];
                }

                dp[i][n + j] = left + middle + right;
            }
        }

        return dp[n][n + k];
    }

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);

        System.out.println(trinomial(n, k));
    }
}