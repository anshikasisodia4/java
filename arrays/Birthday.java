
public class Birthday {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        int[] count = new int[n + 2];

        for (int t = 0; t < trials; t++) {
            boolean[] birthdays = new boolean[n];
            int people = 0;

            while (true) {
                int birthday = (int) (Math.random() * n);
                people++;

                if (birthdays[birthday]) {
                    count[people]++;
                    break;
                }

                birthdays[birthday] = true;
            }
        }

        int cumulative = 0;

        for (int i = 1; i < count.length; i++) {
            cumulative += count[i];

            System.out.printf("%d\t%d\t%.6f%n",
                              i,
                              count[i],
                              (double) cumulative / trials);

            if ((double) cumulative / trials >= 0.5) {
                break;
            }
        }
    }
}