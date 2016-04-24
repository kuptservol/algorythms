package ru.skuptsov.algorythms.graph.presentation;

import java.util.Iterator;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public interface Graph<V extends Vertex, E extends Edge> {

    void addEdge(E edge);

    Iterable<V> vertexFrom(V vertex);

    Iterable<E> edgesFrom(Vertex vertex);

    int vNum();

    int eNum();

    Iterable<V> vertexes();
}
