package ru.skuptsov.algorythms.structure.graph.adjacency.list;

import ru.skuptsov.algorythms.structure.graph.AbstractGraph;
import ru.skuptsov.algorythms.structure.graph.Edge;
import ru.skuptsov.algorythms.structure.graph.Vertex;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public class AdjacencyListDirectedGraph<E extends Edge<P>, P> extends AbstractGraph<P, Vertex<P>, E> {

    private final Set<Vertex<P>> vertexIndex = new HashSet<>();
    protected Map<Vertex<P>, Set<E>> adj;

    public AdjacencyListDirectedGraph() {
        adj = new HashMap<>();
    }

    private static <K, V> V putIfAbsent(Map<K, V> map, K key, V value) {
        V prev = map.get(key);
        if (null == prev) {
            map.put(key, value);
            prev = value;
        }

        return prev;
    }

    /**
     * O(1)
     *
     * @param edge
     */
    @Override
    public void addEdge(E edge) {
        if (!vertexIndex.contains(edge.getFromV())) {
            vertexIndex.add(edge.getFromV());
            vNum++;
        }
        if (!vertexIndex.contains(edge.getToV())) {
            vertexIndex.add(edge.getToV());
            vNum++;
        }

        createEdge(edge);
        eNum++;
    }

    protected void createEdge(E edge) {
        putIfAbsent(adj, edge.getFromV(), new HashSet<>()).add(edge);
    }

    /**
     * O(1)
     *
     * @param vertex
     * @return
     */
    @Override
    public Iterable<Vertex<P>> vertexFrom(Vertex vertex) {
        return adj.get(vertex).stream().map(Edge::getToV).collect(toList());
    }

    /**
     * O(1)
     *
     * @param vertex
     * @return
     */
    @Override
    public Iterable<E> edgesFrom(Vertex<P> vertex) {
        return adj.get(vertex);
    }

    @Override
    public Iterable<Vertex<P>> vertexes() {
        return adj.keySet();
    }

    @Override
    public Iterable<E> edges() {
        return adj.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }
}
