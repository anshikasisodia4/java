
public class ShannonEntropy {

    public static void main(String[] args) {

        int m = Integer.parseInt(args[0]);

        int[] count = new int[m + 1];
        int total = 0;

        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            count[x]++;
            total++;
        }

        double entropy = 0.0;

        for (int i = 1; i <= m; i++) {
            if (count[i] > 0) {
                double p = (double) count[i] / total;
                entropy -= p * (Math.log(p) / Math.log(2));
            }
        }

        System.out.printf("%.4f%n", entropy);
    }
}