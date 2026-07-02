import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.Bag;

import java.util.HashMap;
import java.util.Map;

public class BaseballElimination {

    private final int n;                 // number of teams
    private final String[] teams;        // index -> team name
    private final Map<String, Integer> teamIndex; // team name -> index
    private final int[] w;               // wins
    private final int[] l;               // losses
    private final int[] r;               // remaining
    private final int[][] g;             // g[i][j] = games left between i and j

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        n = Integer.parseInt(in.readLine().trim());

        teams = new String[n];
        teamIndex = new HashMap<>();
        w = new int[n];
        l = new int[n];
        r = new int[n];
        g = new int[n][n];

        for (int i = 0; i < n; i++) {
            String[] parts = in.readLine().trim().split("\\s+");
            teams[i] = parts[0];
            teamIndex.put(parts[0], i);
            w[i] = Integer.parseInt(parts[1]);
            l[i] = Integer.parseInt(parts[2]);
            r[i] = Integer.parseInt(parts[3]);
            for (int j = 0; j < n; j++) {
                g[i][j] = Integer.parseInt(parts[4 + j]);
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        Bag<String> bag = new Bag<>();
        for (String t : teams) bag.add(t);
        return bag;
    }

    private void validateTeam(String team) {
        if (team == null || !teamIndex.containsKey(team))
            throw new IllegalArgumentException("Invalid team: " + team);
    }

    // number of wins for given team
    public int wins(String team) {
        validateTeam(team);
        return w[teamIndex.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        validateTeam(team);
        return l[teamIndex.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        validateTeam(team);
        return r[teamIndex.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validateTeam(team1);
        validateTeam(team2);
        return g[teamIndex.get(team1)][teamIndex.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        validateTeam(team);
        return certificateOfElimination(team) != null;
    }

    // subset R of teams that
// certificate of elimination for given team; null if not eliminated

    public Iterable<String> certificateOfElimination(String team) {
        validateTeam(team);
        int x = teamIndex.get(team);

        Bag<String> certificate = new Bag<>();

        // Trivial elimination: some other team has already won more games
        // than x could possibly finish with
        int maxPossibleWins = w[x] + r[x];
        for (int i = 0; i < n; i++) {
            if (i == x) continue;
            if (w[i] > maxPossibleWins) {
                certificate.add(teams[i]);
            }
        }
        if (certificate.iterator().hasNext()) {
            return certificate;
        }

        // Nontrivial elimination via max-flow
        // Build flow network
        // Vertices: 0 = source, game vertices, team vertices, sink
        int numTeamsExcl = n - 1;
        int numGameVertices = numTeamsExcl * (numTeamsExcl - 1) / 2;
        int V = 1 + numGameVertices + numTeamsExcl + 1;
        int s = 0;
        int t = V - 1;

        FlowNetwork network = new FlowNetwork(V);

        // map from actual team index (excluding x) -> compact team-vertex id
        int[] teamVertex = new int[n];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            if (i == x) continue;
            teamVertex[i] = numGameVertices + 1 + idx; // offset after source+games
            idx++;
        }

        int gameVertex = 1; // start right after source
        double flowCap = 0.0;

        for (int i = 0; i < n; i++) {
            if (i == x) continue;
            for (int j = i + 1; j < n; j++) {
                if (j == x) continue;
                // source -> game vertex, capacity g[i][j]
                network.addEdge(new FlowEdge(s, gameVertex, g[i][j]));
                // game vertex -> team i, infinite capacity
                network.addEdge(new FlowEdge(gameVertex, teamVertex[i], Double.POSITIVE_INFINITY));
                // game vertex -> team j, infinite capacity
                network.addEdge(new FlowEdge(gameVertex, teamVertex[j], Double.POSITIVE_INFINITY));
                gameVertex++;
            }
            // team i -> sink, capacity = maxPossibleWins - w[i]
            double cap = maxPossibleWins - w[i];
            if (cap < 0) cap = 0; // safety, though trivial case already handled above
            network.addEdge(new FlowEdge(teamVertex[i], t, cap));
        }

        FordFulkerson ff = new FordFulkerson(network, s, t);

        boolean eliminated = false;
        for (int i = 0; i < n; i++) {
            if (i == x) continue;
            if (ff.inCut(teamVertex[i])) {
                eliminated = true;
                certificate.add(teams[i]);
            }
        }

        if (!eliminated) {
            return null;
        }
        return certificate;
    }
}