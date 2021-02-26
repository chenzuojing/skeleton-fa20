package bearmaps.proj2ab;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.List;

public class KDTree implements PointSet {
    private Node root;

    private static class Node {
        Point point;
        Node left, right;
        int depth;

        public Node(Point p, int d) {
            this.point = p;
            this.depth = d;
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points)
            insert(p);
    }

    private void insert(Point point) {
        if (root == null) root = new Node(point, 0);
        else root = insert(root, root, point);
    }

    private Node insert(Node parentNode, Node node, Point point) {
        if (node == null) return new Node(point, parentNode.depth + 1);
        if (point.equals(node.point)) {
            node.point = point;
            return node;
        }

        if (node.depth % 2 == 0) {
            if (point.getX() < node.point.getX())
                node.left = insert(node, node.left, point);
            else
                node.right = insert(node, node.right, point);
        } else {
            if (point.getY() < node.point.getY())
                node.left = insert(node, node.left, point);
            else
                node.right = insert(node, node.right, point);
        }
        return node;
    }

    @Override
    public Point nearest(double x, double y) {
        return nearest(root, new Point(x, y), root).point;
    }

    private Node nearestNaive(Node node, Point goal, Node best) {
        if (node == null)
            return best;
        if (Point.distance(node.point, goal) < Point.distance(best.point, goal))
            best = node;
        best = nearestNaive(node.left, goal, best);
        best = nearestNaive(node.right, goal, best);
        return best;
    }

    private Node nearest(Node node, Point goal, Node best) {
        if (node == null)
            return best;
        if (Point.distance(node.point, goal) < Point.distance(best.point, goal))
            best = node;

        Node good = node.left, bad = node.right;
        boolean badPossible;
        if (node.depth % 2 == 0) {
            if (goal.getX() >= node.point.getX()) {
                good = node.right;
                bad = node.left;
            }
            badPossible = Math.abs(node.point.getX() - goal.getX()) < Math.sqrt(Point.distance(best.point, goal));
        } else {
            if (goal.getY() >= node.point.getY()) {
                good = node.right;
                bad = node.left;
            }
            badPossible = Math.abs(node.point.getY() - goal.getY()) < Math.sqrt(Point.distance(best.point, goal));
        }

        best = nearest(good, goal, best);
        if (badPossible)
            best = nearest(bad, goal, best);

        return best;
    }

    public static void main(String[] args) {

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            points.add(new Point(StdRandom.uniform() * 100, StdRandom.uniform() * 100));
        }
        KDTree root = new KDTree(points);

        Stopwatch time = new Stopwatch();

        for (int i = 1; i <= 1000000; i++) {
            double x = StdRandom.uniform() * 100;
            double y = StdRandom.uniform() * 100;
            Point na1 = root.nearest(x, y);
        }

        System.out.println(time.elapsedTime());
    }
}
