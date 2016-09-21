package ru.skuptsov.algorythms.graph.shortestpath;

import ru.skuptsov.algorythms.structure.graph.Edge;
import ru.skuptsov.algorythms.structure.graph.Vertex;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public interface ShortestPath {

    double distTo(Vertex v);

    boolean hasPathTo(Vertex v);

    Iterable<Edge> pathTo(Vertex v);
}
