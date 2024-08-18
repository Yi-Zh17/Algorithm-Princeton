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

public class FastCollinearPoints {
    private int num = 0;
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();


    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (Point i : points) if (i == null) throw new IllegalArgumentException();

        Arrays.sort(points);
        // Only consider lower point as the pivot
        for (int i = 0; i < points.length - 3; i++) {
            Point pivot = points[i];
            Point[] temp = new Point[points.length - i];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = points[j + i]; // Copy larger points into a new array
            }
            Arrays.sort(temp, pivot.slopeOrder());

            int count = 1;
            double slope = pivot.slopeTo(temp[0]);
            for (int index = 1; index < temp.length; index++) {
                double newSlope = pivot.slopeTo(temp[index]);
                if (newSlope == slope) {
                    count++;
                }
                else {
                    if (count >= 3) {
                        Point end = temp[index - 1];
                        lineSegments.add(new LineSegment(pivot, end));
                        num++;
                    }
                    count = 1;
                    slope = newSlope;
                }
            }
            if (count >= 3) {
                Point end = temp[temp.length - 1];
                lineSegments.add(new LineSegment(pivot, end));
                num++;
            }
        }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
