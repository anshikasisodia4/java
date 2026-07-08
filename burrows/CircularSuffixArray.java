import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {

    private final int n;
    private final Integer[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("argument to constructor cannot be null");
        }

        n = s.length();
        index = new Integer[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }

        final String text = s;

        Arrays.sort(index, new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                if (a.equals(b)) return 0;
                for (int k = 0; k < n; k++) {
                    char ca = text.charAt((a + k) % n);
                    char cb = text.charAt((b + k) % n);
                    if (ca != cb) return ca - cb;
                }
                return 0;
            }
        });
    }

    // length of s
    public int length() {
        return n;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= n) {
            throw new IllegalArgumentException("i is outside its prescribed range");
        }
        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(s);

        StdOut.println("length = " + csa.length());
        StdOut.println("i\tindex[i]");
        for (int i = 0; i < csa.length(); i++) {
            StdOut.println(i + "\t" + csa.index(i));
        }

        // test corner cases
        try {
            new CircularSuffixArray(null);
            StdOut.println("FAILED: expected IllegalArgumentException for null argument");
        }
        catch (IllegalArgumentException e) {
            StdOut.println("PASSED: null argument threw IllegalArgumentException");
        }

        try {
            csa.index(-1);
            StdOut.println("FAILED: expected IllegalArgumentException for i = -1");
        }
        catch (IllegalArgumentException e) {
            StdOut.println("PASSED: i = -1 threw IllegalArgumentException");
        }

        try {
            csa.index(csa.length());
            StdOut.println("FAILED: expected IllegalArgumentException for i = n");
        }
        catch (IllegalArgumentException e) {
            StdOut.println("PASSED: i = n threw IllegalArgumentException");
        }

        // empty string edge case
        CircularSuffixArray empty = new CircularSuffixArray("");
        StdOut.println("empty string length = " + empty.length());
    }
}