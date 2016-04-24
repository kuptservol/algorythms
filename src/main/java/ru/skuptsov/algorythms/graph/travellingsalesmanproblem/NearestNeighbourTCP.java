package ru.skuptsov.algorythms.graph.travellingsalesmanproblem;

import ru.skuptsov.algorythms.graph.presentation.Graph;
import ru.skuptsov.algorythms.graph.presentation.Vertex;
import ru.skuptsov.algorythms.graph.presentation.WeightedEdge;
import ru.skuptsov.algorythms.graph.presentation.adjacency.list.AdjacencyListDirectedGraph;

import java.util.*;

/**
 * @author Sergey Kuptsov
 * @since 24/04/2016
 */
public class NearestNeighbourTCP<P> {

    private final Graph<Vertex<P>, WeightedEdge<P>> graph;

    public NearestNeighbourTCP(Graph<Vertex<P>, WeightedEdge<P>> graph) {
        this.graph = graph;
    }

    public List<WeightedEdge<P>> tcp() {
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

    public static void main(String[] args) {

        Graph<Vertex<String>, WeightedEdge<String>> graph = new AdjacencyListDirectedGraph<>();

        Vertex<String> london = new Vertex<>("London");
        Vertex<String> belfast = new Vertex<>("Belfast");
        Vertex<String> dublin = new Vertex<>("Dublin");

        graph.addEdge(new WeightedEdge<>(london, dublin, 464d));
        graph.addEdge(new WeightedEdge<>(dublin, london, 464d));

        graph.addEdge(new WeightedEdge<>(london, belfast, 518d));
        graph.addEdge(new WeightedEdge<>(belfast, london, 518d));

        graph.addEdge(new WeightedEdge<>(dublin, belfast, 141d));
        graph.addEdge(new WeightedEdge<>(belfast, dublin, 141d));


        NearestNeighbourTCP<String> tcp = new NearestNeighbourTCP<>(graph);
        System.out.println(tcp.tcp().stream().map(WeightedEdge::getWeight).reduce((a, b) -> a + b));
    }
}
