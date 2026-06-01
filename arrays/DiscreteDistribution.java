
public class DiscreteDistribution {

    public static void main(String[] args) {

        int m = Integer.parseInt(args[0]);

        int n = args.length - 1;
        int[] a = new int[n];

        int total = 0;
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(args[i + 1]);
            total += a[i];
        }

        for (int t = 0; t < m; t++) {

            int r = (int) (Math.random() * total);

            int sum = 0;

            for (int i = 0; i < n; i++) {
                sum += a[i];

                if (r < sum) {
                    System.out.print(i + " ");
                    break;
                }
            }
        }

        System.out.println();
    }
}