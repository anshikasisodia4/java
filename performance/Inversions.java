public class Inversions {

    // Returns the number of inversions in a[].
    public static long count(int[] a) {
        int[] aux = new int[a.length];
        return count(a, aux, 0, a.length - 1);
    }

    // Returns the number of inversions in a[lo..hi].
    private static long count(int[] a, int[] aux, int lo, int hi) {
        if (lo >= hi) {
            return 0;
        }

        int mid = lo + (hi - lo) / 2;

        long inversions = 0;
        inversions += count(a, aux, lo, mid);
        inversions += count(a, aux, mid + 1, hi);
        inversions += merge(a, aux, lo, mid, hi);

        return inversions;
    }

    // Merges two sorted subarrays and counts split inversions.
    private static long merge(int[] a, int[] aux,
                              int lo, int mid, int hi) {

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        int i = lo;
        int j = mid + 1;
        long inversions = 0;

        for (int k = lo; k <= hi; k++) {

            if (i > mid) {
                a[k] = aux[j++];
            }
            else if (j > hi) {
                a[k] = aux[i++];
            }
            else if (aux[i] <= aux[j]) {
                a[k] = aux[i++];
            }
            else {
                a[k] = aux[j++];
                inversions += (mid - i + 1);
            }
        }

        return inversions;
    }

    public static void main(String[] args) {

        int[] a = { 3, 1, 2 };
        System.out.println(count(a)); // 2

        int[] b = { 8, 4, 2, 1 };
        System.out.println(count(b)); // 6
    }
}