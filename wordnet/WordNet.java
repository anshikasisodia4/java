import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordNet {

    private final Map<Integer, String> idToSynset;
    private final Map<String, ArrayList<Integer>> nounToIds;
    private final Digraph graph;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();

        idToSynset = new HashMap<>();
        nounToIds = new HashMap<>();

        // Read synsets
        In in = new In(synsets);
        int count = 0;

        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");

            int id = Integer.parseInt(fields[0]);
            String synset = fields[1];

            idToSynset.put(id, synset);

            String[] nouns = synset.split(" ");

            for (String noun : nouns) {
                nounToIds.putIfAbsent(noun, new ArrayList<>());
                nounToIds.get(noun).add(id);
            }

            count++;
        }

        // Build graph
        graph = new Digraph(count);

        In hyp = new In(hypernyms);

        while (hyp.hasNextLine()) {
            String[] fields = hyp.readLine().split(",");

            int syn = Integer.parseInt(fields[0]);

            for (int i = 1; i < fields.length; i++) {
                graph.addEdge(syn, Integer.parseInt(fields[i]));
            }
        }

        // Check for cycle
        DirectedCycle cycle = new DirectedCycle(graph);
        if (cycle.hasCycle())
            throw new IllegalArgumentException("Graph is not a DAG");

        // Check rooted DAG
        int roots = 0;

        for (int v = 0; v < graph.V(); v++) {
            if (graph.outdegree(v) == 0)
                roots++;
        }

        if (roots != 1)
            throw new IllegalArgumentException("Graph is not rooted");

        sap = new SAP(graph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounToIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();

        return nounToIds.containsKey(word);
    }

    // distance between nounA and nounB
    public int distance(String nounA, String nounB) {

        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();

        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();

        return sap.length(nounToIds.get(nounA), nounToIds.get(nounB));
    }

    // common ancestor in shortest ancestral path
    public String sap(String nounA, String nounB) {

        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();

        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();

        int ancestor = sap.ancestor(nounToIds.get(nounA),
                                    nounToIds.get(nounB));

        if (ancestor == -1)
            return null;

        return idToSynset.get(ancestor);
    }

    // unit testing
    public static void main(String[] args) {

        if (args.length < 2) {
            StdOut.println("Usage:");
            StdOut.println("java WordNet synsets.txt hypernyms.txt");
            return;
        }

        WordNet wn = new WordNet(args[0], args[1]);

        StdOut.println("Total nouns:");

        int count = 0;
        for (String s : wn.nouns())
            count++;

        StdOut.println(count);

        StdOut.println("Is 'bird' a noun? " + wn.isNoun("bird"));

        if (wn.isNoun("bird") && wn.isNoun("worm")) {
            StdOut.println("Distance = "
                    + wn.distance("bird", "worm"));

            StdOut.println("SAP = "
                    + wn.sap("bird", "worm"));
        }
    }
}