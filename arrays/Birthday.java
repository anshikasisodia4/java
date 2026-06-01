
import java.util.Random;

public class Birthday {

    // --- 1. Mathematical (exact probability) ---
    public static double probability(int n) {
        if (n >= 365) return 1.0;
        double p = 1.0;
        for (int i = 0; i < n; i++) {
            p *= (365.0 - i) / 365.0;
        }
        return 1.0 - p;  // complement: P(at least one shared)
    }

    // --- 2. Monte Carlo simulation ---
    public static double simulate(int groupSize, int trials) {
        Random rand = new Random();
        int matches = 0;

        for (int t = 0; t < trials; t++) {
            boolean[] birthdays = new boolean[365];
            boolean shared = false;

            for (int i = 0; i < groupSize; i++) {
                int day = rand.nextInt(365);
                if (birthdays[day]) {
                    shared = true;
                    break;
                }
                birthdays[day] = true;
            }
            if (shared) matches++;
        }
        return (double) matches / trials;
    }

    // --- 3. Find minimum group size for a target probability ---
    public static int minGroupForProbability(double target) {
        for (int n = 1; n <= 365; n++) {
            if (probability(n) >= target) return n;
        }
        return 365;
    }

    public static void main(String[] args) {
        System.out.println("=== Birthday Paradox ===\n");

        // Print probability table
        System.out.printf("%-10s %-12s %-12s%n", "Group", "Math", "Simulation");
        System.out.println("-".repeat(36));

        int[] groups = {10, 20, 23, 30, 40, 50, 60, 70};
        for (int n : groups) {
            double math = probability(n);
            double sim  = simulate(n, 100_000);
            System.out.printf("%-10d %-12.1f %-12.1f%n",
                n,
                math * 100,
                sim  * 100);
        }

        // Milestone thresholds
        System.out.println("\n=== Milestones ===");
        for (int pct : new int[]{50, 75, 90, 99}) {
            int n = minGroupForProbability(pct / 100.0);
            System.out.printf("%d%% probability needs %d people%n", pct, n);
        }
    }
}