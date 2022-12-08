/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    final private List<LineSegment> segmentsList = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        checkDuplicate(pointsCopy);
        segments(pointsCopy);
    }

    private void checkNull(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point p: points) {
            if (p == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkDuplicate(Point[] points) {
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Points have to be unique");
            }
        }
    }

    public int numberOfSegments() {
        return segmentsList.size();
    }

    private void segments(Point[] points) {
        int pointsNum = points.length;
        Arrays.sort(points);
        for (int i = 0; i < pointsNum - 3; i++) {
            Point p = points[i];
            for (int j = i + 1; j < pointsNum - 2; j++) {
                Point q = points[j];
                double pqSlope = p.slopeTo(q);
                for (int k = j + 1; k < pointsNum - 1; k++) {
                    Point r = points[k];
                    double prSlope = p.slopeTo(r);
                    if (Double.compare(pqSlope, prSlope) == 0) {
                        for (int z = k + 1; z < pointsNum; z++) {
                            Point s = points[z];
                            double psSlope = p.slopeTo(s);
                            if (Double.compare(pqSlope, psSlope) == 0) {
                                segmentsList.add(new LineSegment(p, s));
                            }
                        }
                    }
                }
            }
        }
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segmentsList.size()];
        segmentsList.toArray(segments);
        return segments;
    }
}
