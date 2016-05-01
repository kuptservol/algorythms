package ru.skuptsov.algorythms.graph.presentation;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public interface Graph<P, V extends Vertex<P>, E extends Edge<P>> {

    void addEdge(E edge);

    Iterable<V> vertexFrom(V vertex);

    Iterable<E> edgesFrom(Vertex<P> vertex);

    int vNum();

    int eNum();

    Iterable<V> vertexes();

    Iterable<E> edges();
}
