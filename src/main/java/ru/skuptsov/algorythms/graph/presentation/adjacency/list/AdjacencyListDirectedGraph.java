package ru.skuptsov.algorythms.graph.presentation.adjacency.list;

import com.sun.istack.internal.NotNull;
import ru.skuptsov.algorythms.graph.presentation.AbstractGraph;
import ru.skuptsov.algorythms.graph.presentation.Edge;
import ru.skuptsov.algorythms.graph.presentation.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public class AdjacencyListDirectedGraph<E extends Edge, P> extends AbstractGraph<Vertex<P>, E> {

    protected Map<Vertex<P>, Set<E>> adj;
    private final Set<Vertex<P>> vertexIndex = new HashSet<>();

    public AdjacencyListDirectedGraph() {
        adj = new HashMap<>();
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

    private static <K, V> V putIfAbsent(@NotNull Map<K, V> map, @NotNull K key, @NotNull V value) {
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
     * @param vertex
     * @return
     */
    @Override
    public Iterable<Vertex> vertexFrom(Vertex vertex) {
        return adj.get(vertex).stream().map(Edge::getToV).collect(toList());
    }

    /**
     * O(1)
     *
     * @param vertex
     * @return
     */
    @Override
    public Iterable<E> edgesFrom(Vertex vertex) {
        return adj.get(vertex);
    }

    @Override
    public Iterable<Vertex<P>> vertexes() {
        return adj.keySet();
    }
}
