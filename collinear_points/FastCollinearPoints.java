/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    final private List<LineSegment> segmentsList = new ArrayList<>();
    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        Point[] pointsCopy = points.clone();
        checkDuplicate(pointsCopy);
        segments(pointsCopy);
    }

    private void checkNull(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Points array is null");
        for (Point p: points) {
            if (p == null) {
                throw new IllegalArgumentException("Point object is null");
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

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segmentsList.size()];
        segmentsList.toArray(segments);
        return segments;
    }

    private void segments(Point[] points) {
        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(points); // set to original order
            Arrays.sort(points, points[i].slopeOrder());
            Point origin = points[0]; // origin goes first
            int j = 1;
            for (int k = j + 1; k < points.length; k++) {
                while (k < points.length && Double.compare(origin.slopeTo(points[j]), origin.slopeTo(points[k])) == 0) {
                    k++;
                }
                int checkSize = k - j;
                boolean originIsBottom = origin.compareTo(points[j]) < 0; // do not include the subsegments p→s or q→t.
                if (checkSize >= 3 && originIsBottom) {
                    segmentsList.add(new LineSegment(origin, points[k - 1]));
                }
                j = k;
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
             int x = in.readInt();
             int y = in.readInt();
             points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
