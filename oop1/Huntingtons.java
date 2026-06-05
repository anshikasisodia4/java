public class Huntingtons {

    // Returns the maximum number of consecutive repeats of CAG in the DNA string.
    public static int maxRepeats(String dna) {
        int max = 0;

        for (int i = 0; i <= dna.length() - 3; i++) {
            int count = 0;
            int j = i;

            while (j <= dna.length() - 3 &&
                   dna.substring(j, j + 3).equals("CAG")) {
                count++;
                j += 3;
            }

            if (count > max) {
                max = count;
            }
        }

        return max;
    }

    // Returns a copy of s, with all whitespace removed.
    public static String removeWhitespace(String s) {
        s = s.replace(" ", "");
        s = s.replace("\n", "");
        s = s.replace("\t", "");
        return s;
    }

    // Returns the diagnosis corresponding to the maximum number of repeats.
    public static String diagnose(int maxRepeats) {
        if (maxRepeats <= 9) {
            return "not human";
        }
        if (maxRepeats <= 35) {
            return "normal";
        }
        if (maxRepeats <= 39) {
            return "high risk";
        }
        if (maxRepeats <= 180) {
            return "Huntington's";
        }
        return "not human";
    }

    // Sample client.
    public static void main(String[] args) {
        In in = new In(args[0]);

        String dna = in.readAll();
        dna = removeWhitespace(dna);

        int repeats = maxRepeats(dna);

        StdOut.println("max repeats = " + repeats);
        StdOut.println("diagnosis   = " + diagnose(repeats));
    }
}