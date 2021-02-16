package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private Node root;
    //private Node best;

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
        //best = root;
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
        return nearestNaive(root, new Point(x, y), root).point;
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

    public static void main(String[] args) {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(4, 2);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));

        Point ret = kd.nearest(0.0, 7.0); // returns p2

        System.out.println(ret.toString());
    }
}
