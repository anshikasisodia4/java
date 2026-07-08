import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    private static final int R = 256;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int n = s.length();

        int first = -1;
        char[] t = new char[n];

        for (int i = 0; i < n; i++) {
            int index = csa.index(i);
            if (index == 0) {
                first = i;
                t[i] = s.charAt(n - 1);
            }
            else {
                t[i] = s.charAt(index - 1);
            }
        }

        BinaryStdOut.write(first);
        for (int i = 0; i < n; i++) {
            BinaryStdOut.write(t[i], 8);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String t = BinaryStdIn.readString();
        int n = t.length();
        char[] last = t.toCharArray();

        // count occurrences of each character
        int[] count = new int[R + 1];
        for (int i = 0; i < n; i++) {
            count[last[i] + 1]++;
        }
        for (int r = 0; r < R; r++) {
            count[r + 1] += count[r];
        }

        // next[i] = row in sorted order where the next original suffix appears
        int[] next = new int[n];
        int[] countCopy = new int[R + 1];
        System.arraycopy(count, 0, countCopy, 0, R + 1);

        for (int i = 0; i < n; i++) {
            char c = last[i];
            next[countCopy[c]] = i;
            countCopy[c]++;
        }

        char[] result = new char[n];
        int row = next[first];
        for (int i = 0; i < n; i++) {
            result[i] = last[row];
            row = next[row];
        }

        BinaryStdOut.write(new String(result));
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected exactly one argument: \"-\" or \"+\"");
        }

        if (args[0].equals("-")) {
            transform();
        }
        else if (args[0].equals("+")) {
            inverseTransform();
        }
        else {
            throw new IllegalArgumentException("Argument must be \"-\" (transform) or \"+\" (inverse transform)");
        }
    }
}