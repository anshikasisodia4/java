import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();

        graph = new Digraph(G);   // defensive copy
    }

    // validate vertex
    private void validateVertex(int v) {
        if (v < 0 || v >= graph.V())
            throw new IllegalArgumentException();
    }

    // validate iterable
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null)
            throw new IllegalArgumentException();

        for (Integer v : vertices) {
            if (v == null)
                throw new IllegalArgumentException();
            validateVertex(v);
        }
    }

    // helper method
    private int[] findSAP(Iterable<Integer> v, Iterable<Integer> w) {

        BreadthFirstDirectedPaths bfsV =
                new BreadthFirstDirectedPaths(graph, v);

        BreadthFirstDirectedPaths bfsW =
                new BreadthFirstDirectedPaths(graph, w);

        int minDist = Integer.MAX_VALUE;
        int ancestor = -1;

        for (int i = 0; i < graph.V(); i++) {

            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {

                int dist = bfsV.distTo(i) + bfsW.distTo(i);

                if (dist < minDist) {
                    minDist = dist;
                    ancestor = i;
                }
            }
        }

        if (ancestor == -1)
            return new int[]{-1, -1};

        return new int[]{minDist, ancestor};
    }

    // length between two vertices
    public int length(int v, int w) {

        validateVertex(v);
        validateVertex(w);

        return findSAP(
                java.util.Arrays.asList(v),
                java.util.Arrays.asList(w)
        )[0];
    }

    // ancestor between two vertices
    public int ancestor(int v, int w) {

        validateVertex(v);
        validateVertex(w);

        return findSAP(
                java.util.Arrays.asList(v),
                java.util.Arrays.asList(w)
        )[1];
    }

    // length between two iterable sets
    public int length(Iterable<Integer> v,
                      Iterable<Integer> w) {

        validateVertices(v);
        validateVertices(w);

        return findSAP(v, w)[0];
    }

    // ancestor between two iterable sets
    public int ancestor(Iterable<Integer> v,
                        Iterable<Integer> w) {

        validateVertices(v);
        validateVertices(w);

        return findSAP(v, w)[1];
    }

    // unit testing
    public static void main(String[] args) {

        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        SAP sap = new SAP(G);

        while (!StdIn.isEmpty()) {

            int v = StdIn.readInt();
            int w = StdIn.readInt();

            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);

            StdOut.printf(
                    "length = %d, ancestor = %d\n",
                    length,
                    ancestor
            );
        }
    }
}