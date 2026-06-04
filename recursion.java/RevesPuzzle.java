public class RevesPuzzle {

    // Solves the 3-pole Towers of Hanoi problem.
    private static void hanoi(int n, char from, char to, char aux) {
        if (n == 0) {
            return;
        }

        hanoi(n - 1, from, aux, to);
        System.out.println("Move disc " + n + " from " + from + " to " + to);
        hanoi(n - 1, aux, to, from);
    }

    // Solves Reve's puzzle using four poles.
    private static void reves(int n, char from, char to,
                              char aux1, char aux2) {

        if (n == 0) {
            return;
        }

        if (n == 1) {
            System.out.println("Move disc 1 from " + from + " to " + to);
            return;
        }

        int k = (int) Math.round(n + 1 - Math.sqrt(2 * n + 1));

        reves(k, from, aux1, to, aux2);
        hanoi(n - k, from, to, aux2);
        reves(k, aux1, to, from, aux2);
    }

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);

        reves(n, 'A', 'D', 'B', 'C');
    }
}