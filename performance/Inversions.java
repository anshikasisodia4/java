public class Inversions {

    // Return the number of inversions in the permutation a[].
    public static long count(int[] a) {
        long inversions = 0;

        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    inversions++;
                }
            }
        }

        return inversions;
    }

    // Return a permutation of length n with exactly k inversions.
    public static int[] generate(int n, long k) {
        int[] perm = new int[n];

        int left = 0;
        int right = n - 1;
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            long maxInv = high - low;

            if (k >= maxInv) {
                perm[left++] = high;
                k -= maxInv;
                high--;
            }
            else {
                perm[right--] = high;
                high--;
            }
        }

        return perm;
    }

    // Takes an integer n and a long k as command-line arguments,
    // and prints a permutation of length n with exactly k inversions.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        long k = Long.parseLong(args[1]);

        int[] permutation = generate(n, k);

        for (int i = 0; i < permutation.length; i++) {
            System.out.print(permutation[i]);

            if (i < permutation.length - 1) {
                System.out.print(" ");
            }
        }

        System.out.println();
    }
}