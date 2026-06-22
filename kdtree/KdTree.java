import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private Node root;
    private int size;

    private static class Node {
        private final Point2D point;
        private final RectHV rect;
        private Node lb; // left/bottom subtree
        private Node rt; // right/top subtree

        Node(Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
        }
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        root = insert(root, p, true,
                      0.0, 0.0, 1.0, 1.0);
    }

    private Node insert(Node node,
                        Point2D p,
                        boolean vertical,
                        double xmin,
                        double ymin,
                        double xmax,
                        double ymax) {

        if (node == null) {
            size++;
            return new Node(p,
                            new RectHV(xmin, ymin, xmax, ymax));
        }

        if (node.point.equals(p))
            return node;

        if (vertical) {

            if (p.x() < node.point.x()) {
                node.lb = insert(node.lb,
                                 p,
                                 false,
                                 xmin,
                                 ymin,
                                 node.point.x(),
                                 ymax);
            }
            else {
                node.rt = insert(node.rt,
                                 p,
                                 false,
                                 node.point.x(),
                                 ymin,
                                 xmax,
                                 ymax);
            }
        }
        else {

            if (p.y() < node.point.y()) {
                node.lb = insert(node.lb,
                                 p,
                                 true,
                                 xmin,
                                 ymin,
                                 xmax,
                                 node.point.y());
            }
            else {
                node.rt = insert(node.rt,
                                 p,
                                 true,
                                 xmin,
                                 node.point.y(),
                                 xmax,
                                 ymax);
            }
        }

        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        return contains(root, p, true);
    }

    private boolean contains(Node node,
                             Point2D p,
                             boolean vertical) {

        if (node == null)
            return false;

        if (node.point.equals(p))
            return true;

        if (vertical) {
            if (p.x() < node.point.x())
                return contains(node.lb, p, false);
            else
                return contains(node.rt, p, false);
        }
        else {
            if (p.y() < node.point.y())
                return contains(node.lb, p, true);
            else
                return contains(node.rt, p, true);
        }
    }

    // draw all points
    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node == null)
            return;

        node.point.draw();

        draw(node.lb);
        draw(node.rt);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();

        List<Point2D> result = new ArrayList<>();
        range(root, rect, result);

        return result;
    }

    private void range(Node node,
                       RectHV queryRect,
                       List<Point2D> result) {

        if (node == null)
            return;

        if (!queryRect.intersects(node.rect))
            return;

        if (queryRect.contains(node.point))
            result.add(node.point);

        range(node.lb, queryRect, result);
        range(node.rt, queryRect, result);
    }

    // nearest neighbor
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        if (root == null)
            return null;

        return nearest(root, p, root.point, true);
    }

    private Point2D nearest(Node node,
                            Point2D query,
                            Point2D best,
                            boolean vertical) {

        if (node == null)
            return best;

        double bestDist =
                query.distanceSquaredTo(best);

        if (node.rect.distanceSquaredTo(query) >= bestDist)
            return best;

        double nodeDist =
                query.distanceSquaredTo(node.point);

        if (nodeDist < bestDist) {
            best = node.point;
            bestDist = nodeDist;
        }

        Node first;
        Node second;

        if (vertical) {
            if (query.x() < node.point.x()) {
                first = node.lb;
                second = node.rt;
            }
            else {
                first = node.rt;
                second = node.lb;
            }
        }
        else {
            if (query.y() < node.point.y()) {
                first = node.lb;
                second = node.rt;
            }
            else {
                first = node.rt;
                second = node.lb;
            }
        }

        best = nearest(first, query, best, !vertical);
        best = nearest(second, query, best, !vertical);

        return best;
    }

    // unit testing (optional)
    public static void main(String[] args) {

        KdTree kd = new KdTree();

        kd.insert(new Point2D(0.7, 0.2));
        kd.insert(new Point2D(0.5, 0.4));
        kd.insert(new Point2D(0.2, 0.3));
        kd.insert(new Point2D(0.4, 0.7));
        kd.insert(new Point2D(0.9, 0.6));

        System.out.println("Size = " + kd.size());

        Point2D query = new Point2D(0.65, 0.5);

        System.out.println(
                "Nearest = " + kd.nearest(query)
        );

        RectHV rect = new RectHV(
                0.0, 0.0,
                0.5, 0.5
        );

        for (Point2D p : kd.range(rect)) {
            System.out.println(p);
        }
    }
}