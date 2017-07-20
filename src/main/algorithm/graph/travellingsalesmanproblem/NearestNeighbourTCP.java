package algorithm.graph.travellingsalesmanproblem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import algorithm.structure.graph.Graph;
import algorithm.structure.graph.Vertex;
import algorithm.structure.graph.WeightedEdge;

/**
 * @author Sergey Kuptsov
 * @since 24/04/2016
 */
public class NearestNeighbourTCP<P> implements TravellingSalesmanProblem<P> {

    private final Graph<P, Vertex<P>, WeightedEdge<P>> graph;

    public NearestNeighbourTCP(Graph<P, Vertex<P>, WeightedEdge<P>> graph) {
        this.graph = graph;
    }

    public List<WeightedEdge<P>> buildShortestRoute() {
        List<WeightedEdge<P>> tsPath = new ArrayList<>();
        Set<Vertex<P>> visited = new HashSet<>();
        Vertex<P> startVertex = graph.vertexes().iterator().next();
        Stack<Vertex<P>> pathStack = new Stack<>();

        visited.add(startVertex);
        pathStack.push(startVertex);

        while (!pathStack.isEmpty()) {
            Vertex<P> nextV = pathStack.pop();
            Vertex<P> nextVOnPath = null;
            WeightedEdge<P> nextPath = null;
            Double minWeight = Double.MAX_VALUE;
            boolean foundMin = false;

            for (WeightedEdge<P> edge : graph.edgesFrom(nextV)) {
                if (!visited.contains(edge.getToV()) && edge.getWeight() < minWeight) {
                    minWeight = edge.getWeight();
                    nextVOnPath = edge.getToV();
                    nextPath = edge;
                    foundMin = true;
                }
            }

            if (foundMin) {
                pathStack.push(nextVOnPath);
                visited.add(nextV);
                tsPath.add(nextPath);
            }
        }

        return tsPath;
    }
}
