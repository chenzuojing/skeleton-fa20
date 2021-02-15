package bearmaps;

public class KDTreeNode {
    Point point;
    KDTreeNode left;
    KDTreeNode right;
    int depth;

    public KDTreeNode(Point p, int d) {
        this.point = p;
        this.depth = d;
    }

    public Point getPoint() {
        return point;
    }

    public KDTreeNode getLeft() {
        return left;
    }

    public KDTreeNode getRight() {
        return right;
    }

    public int getDepth() {
        return depth;
    }
}
