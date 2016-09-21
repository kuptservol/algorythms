package ru.skuptsov.algorythms.graph.travellingsalesmanproblem;

import com.google.common.collect.ComparisonChain;
import ru.skuptsov.algorythms.structure.graph.Graph;
import ru.skuptsov.algorythms.structure.graph.Vertex;
import ru.skuptsov.algorythms.structure.graph.WeightedEdge;

import java.util.*;

/**
 * @author Sergey Kuptsov
 * @since 01/05/2016
 */
public class ShortestEdgesTCP<P extends Comparable<P>> implements TravellingSalesmanProblem<P> {

    private final Graph<P, Vertex<P>, WeightedEdge<P>> graph;
    private final Map<Vertex<P>, Integer> visitedVertexes = new HashMap<>();
    private final Comparator<WeightedEdge<P>> WEIGHTED_EDGE_COMPARATOR = (o1, o2) -> ComparisonChain.start()
            .compare(o1.getWeight(), o2.getWeight())
            .compare(o1.getFromV().getValue(), o2.getFromV().getValue())
            .compare(o1.getToV().getValue(), o2.getToV().getValue())
            .result();

    public ShortestEdgesTCP(Graph<P, Vertex<P>, WeightedEdge<P>> graph) {
        this.graph = graph;
    }

    @Override
    public List<WeightedEdge<P>> buildShortestRoute() {
        List<WeightedEdge<P>> shortestRoute = new ArrayList<>();


        TreeSet<WeightedEdge<P>> sortedEdges = new TreeSet<>(WEIGHTED_EDGE_COMPARATOR);
        for (WeightedEdge<P> edge : graph.edges()) {
            sortedEdges.add(edge);
        }


        for (WeightedEdge<P> edge : sortedEdges) {
            if ((!visitedVertexes.containsKey(edge.getToV()) || visitedVertexes.get(edge.getToV()) < 2)
                    && (!visitedVertexes.containsKey(edge.getFromV()) || visitedVertexes.get(edge.getFromV()) < 2)) {
                shortestRoute.add(edge);
                if (!visitedVertexes.containsKey(edge.getToV())) {
                    visitedVertexes.put(edge.getToV(), 1);
                } else {
                    visitedVertexes.put(edge.getToV(), visitedVertexes.get(edge.getToV()) + 1);
                }

                if (!visitedVertexes.containsKey(edge.getFromV())) {
                    visitedVertexes.put(edge.getFromV(), 1);
                } else {
                    visitedVertexes.put(edge.getFromV(), visitedVertexes.get(edge.getFromV()) + 1);
                }
            }
        }

        return shortestRoute;
    }
}
