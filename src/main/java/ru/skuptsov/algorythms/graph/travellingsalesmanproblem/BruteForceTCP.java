package ru.skuptsov.algorythms.graph.travellingsalesmanproblem;

import ru.skuptsov.algorythms.graph.presentation.Graph;
import ru.skuptsov.algorythms.graph.presentation.Vertex;
import ru.skuptsov.algorythms.graph.presentation.WeightedEdge;

import java.util.*;

/**
 * @author Sergey Kuptsov
 * @since 01/05/2016
 */
public class BruteForceTCP<P> implements TravellingSalesmanProblem<P> {

    private final Graph<P, Vertex<P>, WeightedEdge<P>> graph;
    List<Map<Vertex<P>, Double>> shortestPaths = new ArrayList<>();


    public BruteForceTCP(Graph<P, Vertex<P>, WeightedEdge<P>> graph) {
        this.graph = graph;
    }

    @Override
    public List<WeightedEdge<P>> buildShortestRoute() {
        for (Vertex<P> vertex : graph.vertexes()) {
            Map<Vertex<P>, Double> path = new LinkedHashMap<>();
            path.put(vertex, 0d);
            countAllRoutes(vertex, path);
        }

        shortestPaths.sort((path1, path2) -> {
            Double path1LastEntry = 0d;
            Iterator<Double> iterator = path1.values().iterator();
            while (iterator.hasNext()) {
                path1LastEntry = iterator.next();
            }

            Iterator<Double> iterator2 = path2.values().iterator();
            Double path2LastEntry = 0d;
            while (iterator2.hasNext()) {
                path2LastEntry = iterator2.next();
            }

            return path1LastEntry.compareTo(path2LastEntry);
        });

        System.out.println(shortestPaths.get(0));

        return null;
    }

    private void countAllRoutes(Vertex<P> vertex, Map<Vertex<P>, Double> path) {
        for (WeightedEdge<P> edge : graph.edgesFrom(vertex)) {
            Vertex<P> connectedVertex = edge.getToV();
            if (path.size() == graph.vNum()) {
                shortestPaths.add(path);
                return;
            }
            if (!path.containsKey(connectedVertex)) {
                Map<Vertex<P>, Double> newPath = new LinkedHashMap<>();
                newPath.putAll(path);
                newPath.put(connectedVertex, path.get(vertex) + edge.getWeight());
                countAllRoutes(connectedVertex, newPath);
            }
        }

    }
}