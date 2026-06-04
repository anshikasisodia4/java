public class RecursiveSquares {

    // Draws a square centered on (x, y) of the given side length
    // with a light gray background and a black border.
    public static void drawSquare(double x, double y, double length) {
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.filledSquare(x, y, length / 2.0);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.square(x, y, length / 2.0);
    }

    // Draws a recursive square pattern of order n.
    public static void draw(int n, double x, double y, double length) {

        if (n == 0) {
            return;
        }

        drawSquare(x, y, length);

        double half = length / 2.0;

        draw(n - 1, x - half, y - half, half);
        draw(n - 1, x - half, y + half, half);
        draw(n - 1, x + half, y - half, half);
        draw(n - 1, x + half, y + half, half);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        draw(n, 0.5, 0.5, 0.5);
    }
}