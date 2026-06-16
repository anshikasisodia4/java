import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {

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

        segments = new ArrayList<>();

        int n = copy.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {

                        double s1 = copy[i].slopeTo(copy[j]);
                        double s2 = copy[i].slopeTo(copy[k]);
                        double s3 = copy[i].slopeTo(copy[l]);

                        if (Double.compare(s1, s2) == 0 &&
                            Double.compare(s1, s3) == 0) {

                            segments.add(
                                new LineSegment(copy[i], copy[l])
                            );
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }
}