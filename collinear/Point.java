import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;

        if (this.x < that.x) return -1;
        if (this.x > that.x) return 1;

        return 0;
    }

    public double slopeTo(Point that) {

        // degenerate line segment
        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;
        }

        // vertical line
        if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        }

        // horizontal line
        if (this.y == that.y) {
            return +0.0;
        }

        return (double) (that.y - this.y) / (that.x - this.x);
    }

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