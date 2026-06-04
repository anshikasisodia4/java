public class Divisors {

    public static int divisors(int n) {
        if (n <= 0) {
            return 0;
        }

        int count = 0;

        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                if (i == n / i) {
                    count++;
                }
                else {
                    count += 2;
                }
            }
        }

        return count;
    }

    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }

    public static int lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }

        return Math.abs(a / gcd(a, b) * b);
    }

    public static boolean areRelativelyPrime(int a, int b) {
        return gcd(a, b) == 1;
    }

    public static int totient(int n) {
        if (n <= 0) {
            return 0;
        }

        int count = 0;

        for (int i = 1; i <= n; i++) {
            if (areRelativelyPrime(i, n)) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        
         int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int n = Integer.parseInt(args[2]);

        System.out.println("Number of divisors = " + divisors(n));
        System.out.println("Euler totient = " + totient(n));

   

            System.out.println("gcd(" + a + ", " + b + ") = " + gcd(a, b));
            System.out.println("lcm(" + a + ", " + b + ") = " + lcm(a, b));
            System.out.println("are Relatively prime? " + areRelativelyPrime(a, b));
        
    }
}