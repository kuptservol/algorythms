package ru.skuptsov.algorythms.graph.shortestpath.dijkstra;

import com.google.common.base.Objects;
import ru.skuptsov.algorythms.graph.presentation.Edge;
import ru.skuptsov.algorythms.graph.presentation.Graph;
import ru.skuptsov.algorythms.graph.presentation.Vertex;
import ru.skuptsov.algorythms.graph.presentation.WeightedEdge;
import ru.skuptsov.algorythms.graph.presentation.adjacency.list.AdjacencyListDirectedGraph;
import ru.skuptsov.algorythms.graph.shortestpath.AbstractShortestPath;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public class DijkstrasShortestPath<P> extends AbstractShortestPath<P> {

    private final Map<Vertex<P>, Edge> edgeTo = new HashMap<>();
    private final Map<Vertex<P>, Double> distTo = new HashMap<>();
    private final PriorityQueue<WeightedVertex> weightedVertexPQ = new PriorityQueue<>();

    public DijkstrasShortestPath(Graph<P, Vertex<P>, WeightedEdge<P>> graph, Vertex fromV) {
        super(graph, fromV);
        weightedVertexPQ.add(new WeightedVertex(fromV, 0.0));
        distTo.put(fromV, 0.0);
        while (!weightedVertexPQ.isEmpty()) {
            relaxVertex(weightedVertexPQ.poll().getVertex());
        }
    }

    public static void main(String[] args) {
        Graph<Integer, Vertex<Integer>, WeightedEdge<Integer>> graph = new AdjacencyListDirectedGraph<>();
        fillGraph(graph);

        DijkstrasShortestPath dijkstrasShortestPath = new DijkstrasShortestPath<>(graph, new Vertex<>(1));

        System.out.println(dijkstrasShortestPath.distTo(new Vertex<>(4)));

    }

    private static void fillGraph(Graph<Integer, Vertex<Integer>, WeightedEdge<Integer>> graph) {
        Vertex<Integer> vertex1 = new Vertex<>(1);
        Vertex<Integer> vertex2 = new Vertex<>(2);
        Vertex<Integer> vertex3 = new Vertex<>(3);
        Vertex<Integer> vertex4 = new Vertex<>(4);
        Vertex<Integer> vertex5 = new Vertex<>(5);
        Vertex<Integer> vertex6 = new Vertex<>(6);


        graph.addEdge(new WeightedEdge(vertex1, vertex2, 7d));
        graph.addEdge(new WeightedEdge(vertex2, vertex1, 7d));

        graph.addEdge(new WeightedEdge(vertex1, vertex6, 14d));
        graph.addEdge(new WeightedEdge(vertex6, vertex1, 14d));

        graph.addEdge(new WeightedEdge(vertex1, vertex3, 9d));
        graph.addEdge(new WeightedEdge(vertex3, vertex1, 9d));

        graph.addEdge(new WeightedEdge(vertex3, vertex6, 2d));
        graph.addEdge(new WeightedEdge(vertex6, vertex3, 2d));

        graph.addEdge(new WeightedEdge(vertex3, vertex4, 11d));
        graph.addEdge(new WeightedEdge(vertex4, vertex3, 11d));

        graph.addEdge(new WeightedEdge(vertex6, vertex5, 9d));
        graph.addEdge(new WeightedEdge(vertex5, vertex6, 9d));

        graph.addEdge(new WeightedEdge(vertex5, vertex4, 6d));
        graph.addEdge(new WeightedEdge(vertex4, vertex5, 6d));

        graph.addEdge(new WeightedEdge(vertex2, vertex4, 15d));
        graph.addEdge(new WeightedEdge(vertex4, vertex2, 15d));

        graph.addEdge(new WeightedEdge(vertex3, vertex2, 10d));
        graph.addEdge(new WeightedEdge(vertex2, vertex3, 10d));

    }

    private void relaxVertex(Vertex<P> v) {
        for (WeightedEdge<P> e : graph.edgesFrom(v)) {
            relaxEdge(e);
        }
    }

    private void relaxEdge(WeightedEdge edge) {
        Vertex to = edge.getToV();
        Vertex from = edge.getFromV();
        if (distToVertex(to) > distToVertex(from) + edge.getWeight()) {
            edgeTo.put(to, edge);
            distTo.put(to, distToVertex(from) + edge.getWeight());

            WeightedVertex indexVertex = new WeightedVertex(to, distTo(to));
            if (weightedVertexPQ.contains(indexVertex)) {
                weightedVertexPQ.remove(indexVertex);
            }
            weightedVertexPQ.add(indexVertex);
        }
    }

    private Double distToVertex(Vertex to) {
        return distTo.containsKey(to) ? distTo.get(to) : Double.POSITIVE_INFINITY;
    }

    @Override
    public double distTo(Vertex v) {
        return distTo.get(v);
    }

    @Override
    public boolean hasPathTo(Vertex v) {
        return distTo.containsKey(v);
    }

    @Override
    public Iterable<Edge> pathTo(Vertex v) {
        if (!hasPathTo(v))
            return null;

        Stack<Edge> path = new Stack<>();
        for (Edge e = edgeTo.get(v); e != null; e = edgeTo.get(e.getFromV()))
            path.add(e);

        return path;
    }

    private static class WeightedVertex implements Comparable<WeightedVertex> {

        private final Vertex vertex;
        private final Double weight;

        public WeightedVertex(Vertex vertex, Double weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WeightedVertex that = (WeightedVertex) o;
            return Objects.equal(vertex, that.vertex);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(vertex);
        }

        public Vertex getVertex() {
            return vertex;
        }

        public Double getWeight() {
            return weight;
        }

        @Override
        public int compareTo(WeightedVertex o) {
            return o.getWeight().compareTo(weight);
        }
    }
}
