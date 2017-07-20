package puzzler.codingame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class SkyNetRevolution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways

        Graph graph = new Graph();
        List<Integer> gateways = new ArrayList<>(E);

        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();

            graph.addEdge(N1, N2);
        }

        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            gateways.add(EI);
        }

        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn

            Graph.Edge edge = nextEdgeToRemove(graph, SI, gateways);
            graph.removeEdge(edge);

            System.out.println(edge.from + " " + edge.to);
        }
    }

    public static Graph.Edge nextEdgeToRemove(Graph graph, int currSkynet, List<Integer> gateways) {
        for (Integer gateway : gateways) {
            Optional<Graph.Edge> edge = graph.getEdge(currSkynet, gateway);

            if (edge.isPresent()) {
                return edge.get();
            }
        }

        return gateways
                .stream()
                .map(graph::getAdjNodes)
                .flatMap(Collection::stream)
                .findFirst()
                .get();
    }

    public static class Graph {
        private static final Map<Integer, Set<Integer>> adj = new HashMap<>();

        public void addEdge(int first, int second) {
            addEdgeOneDir(first, second);
            addEdgeOneDir(second, first);
        }

        public Optional<Edge> getEdge(int first, int second) {
            return adj.get(first).contains(second) ?
                    Optional.of(new Edge(first, second)) :
                    Optional.empty();
        }

        public Set<Integer> adjNodes(int from) {
            return adj.get(from);
        }

        public Set<Edge> getAdjNodes(int from) {
            return adj.get(from)
                    .stream()
                    .map(t -> new Edge(t, from))
                    .collect(Collectors.toSet());
        }

        public void removeEdge(Edge edge) {
            removeEdge(edge.from, edge.to);
        }

        public void removeEdge(int first, int second) {
            removeOneDir(first, second);
            removeOneDir(second, first);
        }

        private void removeOneDir(int first, int second) {
            adj.get(first).remove(second);
        }

        private void addEdgeOneDir(int first, int second) {
            adj.computeIfAbsent(first, (k) -> new HashSet<>()).add(second);
        }

        public static class Edge {
            public final int from;
            public final int to;

            public Edge(int from, int to) {
                this.from = from;
                this.to = to;
            }
        }
    }
}
