public class Ramanujan {

    // Is n a Ramanujan number?
    public static boolean isRamanujan(long n) {

        int count = 0;
        long limit = (long) Math.cbrt(n);

        for (long a = 1; a <= limit; a++) {

            long aCube = a * a * a;
            long target = n - aCube;

            long low = a + 1;
            long high = limit;

            while (low <= high) {
                long mid = low + (high - low) / 2;
                long cube = mid * mid * mid;

                if (cube == target) {
                    count++;
                    if (count == 2) return true;
                    break;
                }
                else if (cube < target) {
                    low = mid + 1;
                }
                else {
                    high = mid - 1;
                }
            }
        }

        return false;
    }

    // Takes a long integer command-line argument n and prints true if
    // n is a Ramanujan number, and false otherwise.
    public static void main(String[] args) {
        long n = Long.parseLong(args[0]);
        System.out.println(isRamanujan(n));
    }
}