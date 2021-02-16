package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = new ArrayList<>(points);
    }

    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        Point best = null;
        double min = Double.MAX_VALUE;
        for (Point p : points) {
            double dist = Math.sqrt(Point.distance(p, target));
            if (dist < min) {
                min = dist;
                best = p;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(4, 2);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6, p7));
        Point ret = nn.nearest(0.0, 7.0); // returns p2

        System.out.println(ret.toString());
    }
}