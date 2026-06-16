import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {

        validate(points);

        Point[] sorted = points.clone();
        Arrays.sort(sorted);

        segments = new ArrayList<>();

        int n = sorted.length;

        for (int i = 0; i < n; i++) {

            Point origin = sorted[i];

            Point[] bySlope = sorted.clone();

            Arrays.sort(bySlope, origin.slopeOrder());

            int first = 1;

            while (first < n) {

                double slope =
                        origin.slopeTo(bySlope[first]);

                int last = first + 1;

                while (last < n &&
                       Double.compare(
                           slope,
                           origin.slopeTo(bySlope[last])) == 0) {

                    last++;
                }

                if (last - first >= 3) {

                    Point min = origin;
                    Point max = origin;

                    for (int k = first; k < last; k++) {

                        if (bySlope[k].compareTo(min) < 0)
                            min = bySlope[k];

                        if (bySlope[k].compareTo(max) > 0)
                            max = bySlope[k];
                    }

                    if (origin.compareTo(min) == 0) {
                        segments.add(
                                new LineSegment(min, max)
                        );
                    }
                }

                first = last;
            }
        }
    }

    private void validate(Point[] points) {

        if (points == null)
            throw new IllegalArgumentException();

        Point[] copy = points.clone();

        for (Point p : copy) {
            if (p == null)
                throw new IllegalArgumentException();
        }

        Arrays.sort(copy);

        for (int i = 1; i < copy.length; i++) {
            if (copy[i].compareTo(copy[i - 1]) == 0)
                throw new IllegalArgumentException();
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }
}