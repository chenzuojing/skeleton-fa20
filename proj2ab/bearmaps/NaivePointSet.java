package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

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
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            points.add(new Point(StdRandom.uniform() * 100, StdRandom.uniform() * 100));
        }
        NaivePointSet nn = new NaivePointSet(points);

        Stopwatch time = new Stopwatch();

        for (int i = 1; i <= 1000000; i++) {
            double x = StdRandom.uniform() * 100;
            double y = StdRandom.uniform() * 100;
            Point na1 = nn.nearest(x, y);
        }

        System.out.println(time.elapsedTime());
    }
}
