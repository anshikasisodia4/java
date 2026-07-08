import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] sequence = initSequence();

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();

            // find position of c in sequence
            int pos = 0;
            while (sequence[pos] != c) pos++;

            // output the position as an 8-bit value
            BinaryStdOut.write((char) pos, 8);

            // move c to the front, shifting the others down
            for (int i = pos; i > 0; i--) {
                sequence[i] = sequence[i - 1];
            }
            sequence[0] = c;
        }

        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] sequence = initSequence();

        while (!BinaryStdIn.isEmpty()) {
            int pos = BinaryStdIn.readChar(8);

            char c = sequence[pos];

            // output the character
            BinaryStdOut.write(c, 8);

            // move c to the front, shifting the others down
            for (int i = pos; i > 0; i--) {
                sequence[i] = sequence[i - 1];
            }
            sequence[0] = c;
        }

        BinaryStdOut.close();
    }

    // initialize the ordered sequence of the 256 extended ASCII characters
    private static char[] initSequence() {
        char[] sequence = new char[R];
        for (int i = 0; i < R; i++) {
            sequence[i] = (char) i;
        }
        return sequence;
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected exactly one argument: \"-\" or \"+\"");
        }

        if (args[0].equals("-")) {
            encode();
        }
        else if (args[0].equals("+")) {
            decode();
        }
        else {
            throw new IllegalArgumentException("Argument must be \"-\" (encode) or \"+\" (decode)");
        }
    }
}