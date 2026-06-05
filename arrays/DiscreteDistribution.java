public class DiscreteDistribution {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = args.length - 1;

        int[] cumulative = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            cumulative[i] = cumulative[i - 1] + Integer.parseInt(args[i]);
        }
        int total = cumulative[n];
    
        for (int k = 0; k < m; k++) {
            int r = (int) (Math.random() * total);
            int lo = 1, hi = n;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (cumulative[mid] <= r) lo = mid + 1;
                else                      hi = mid;
            }

            System.out.print(lo + " ");
        }
        System.out.println();
    }
}