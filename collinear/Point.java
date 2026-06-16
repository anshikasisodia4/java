import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare points by y-coordinate, breaking ties by x-coordinate
    @Override
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;

        if (this.x < that.x) return -1;
        if (this.x > that.x) return 1;

        return 0;
    }

    // slope between this point and that point
    public double slopeTo(Point that) {

        // degenerate line segment
        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;
        }

        // vertical line segment
        if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        }

        // horizontal line segment
        if (this.y == that.y) {
            return +0.0;
        }

        return (double) (that.y - this.y)
                / (that.x - this.x);
    }

    // compare points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new SlopeComparator();
    }

    private class SlopeComparator implements Comparator<Point> {

        @Override
        public int compare(Point p1, Point p2) {
            return Double.compare(
                    slopeTo(p1),
                    slopeTo(p2)
            );
        }
    }
}