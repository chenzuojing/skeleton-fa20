package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    @Test
    public void testNaive() {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(4, 2);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3, p4, p5, p6, p7));
        Point ret = nn.nearest(0.0, 7.0);

        assertEquals(1, ret.getX(), 0.00000001);
        assertEquals(5, ret.getY(), 0.00000001);
    }


    @Test
    public void testKd() {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 1500; i++) {
            points.add(new Point(StdRandom.uniform() * 10, StdRandom.uniform() * 10));
        }
        NaivePointSet nn = new NaivePointSet(points);
        KDTree root = new KDTree(points);

//        Point na1 = nn.nearest(1.0, 1.0);
//        Point kd1 = root.nearest(1.0, 1.0);
//        assertEquals(na1.getX(), kd1.getX(), 0.00000001);
//        assertEquals(na1.getY(), kd1.getY(), 0.00000001);

        for (int i = 1; i <= 100; i++) {
            double x = StdRandom.uniform() * 10;
            double y = StdRandom.uniform() * 10;
            Point na1 = nn.nearest(x, y);
            Point kd1 = root.nearest(x, y);
            assertEquals(na1.getX(), kd1.getX(), 0.00000001);
            assertEquals(na1.getY(), kd1.getY(), 0.00000001);
        }
    }
}
