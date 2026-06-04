
public class Ramanujan {

    // Returns true if n is a Ramanujan number.
    public static boolean isRamanujan(long n) {
        int count = 0;

        for (long a = 1; a * a * a < n; a++) {
            for (long b = a + 1; a * a * a + b * b * b <= n; b++) {
                if (a * a * a + b * b * b == n) {
                    count++;
                }
            }
        }

        return count == 2;
    }

    public static void main(String[] args) {
        long n = Long.parseLong(args[0]);

        for (long i = 1; i <= n; i++) {
            if (isRamanujan(i)) {
                System.out.println(i);
            }
        }
    }
}