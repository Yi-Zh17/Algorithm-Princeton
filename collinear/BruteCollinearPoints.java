/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int num;
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();


    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point i : points) if (i == null) throw new IllegalArgumentException();
        ArrayList<Point[]> duplicateCheck = new ArrayList<>();
        for (int p = 0; p < points.length; p++) {
            for (int q = 0; q < points.length; q++) {
                for (int r = 0; r < points.length; r++) {
                    if (p == q || q == r || p == r) break;
                    double slopePQ = points[p].slopeTo(points[q]);
                    double slopeQR = points[q].slopeTo(points[r]);
                    if (slopePQ == slopeQR) {
                        for (int s = 0; s < points.length; s++) {
                            if (s == p || s == q || s == r) break;
                            double slopeRS = points[r].slopeTo(points[s]);
                            if (slopeQR == slopeRS) {
                                Point[] result = { points[p], points[q], points[r], points[s] };
                                Arrays.sort(result);
                                Point start = result[0];
                                Point end = result[3];
                                LineSegment ls = new LineSegment(result[0], result[3]);
                                if (duplicateCheck.isEmpty() || !contains(duplicateCheck, start,
                                                                          end)) {
                                    lineSegments.add(ls);
                                    duplicateCheck.add(new Point[] { start, end });
                                    num++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean contains(ArrayList<Point[]> dup, Point start, Point end) {
        for (Point[] pt : dup) {
            if (pt[0] == start && pt[1] == end)
                return true;
        }
        return false;
    }

    public int numberOfSegments() {
        return num;
    }

    public LineSegment[] segments() {
        if (lineSegments.size() == 0) return new LineSegment[] { };
        LineSegment[] result = new LineSegment[lineSegments.size()];
        for (int i = 0; i < lineSegments.size(); i++) {
            result[i] = lineSegments.get(i);
        }
        return result;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
