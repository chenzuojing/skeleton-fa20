package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private KDTreeNode root;

    public KDTree(List<Point> points) {
        for (Point p : points)
            insert(p);
    }

    private void insert(Point point) {
        if (root == null) root = new KDTreeNode(point, 0);
        else root = insert(root, root, point);
    }

    private KDTreeNode insert(KDTreeNode parent, KDTreeNode node, Point point) {
        if (node == null) return new KDTreeNode(point, parent.depth + 1);
        if (node.getDepth() % 2 == 0) {
            if (point.equals(node.point))
                node = new KDTreeNode(point, parent.depth + 1);
            else if (point.getX() < node.point.getX())
                node.left = insert(node, node.left, point);
            else
                node.right = insert(node, node.right, point);
        } else {
            if (point.equals(node.point))
                node = new KDTreeNode(point, parent.depth + 1);
            else if (point.getY() < node.point.getY())
                node.left = insert(node, node.left, point);
            else
                node.right = insert(node, node.right, point);
        }
        return node;
    }

    @Override
    public Point nearest(double x, double y) {
        return null;
    }

    public static void main(String[] args) {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
    }
}
