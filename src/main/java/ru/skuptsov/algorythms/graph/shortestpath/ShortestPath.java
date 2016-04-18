package ru.skuptsov.algorythms.graph.shortestpath;

import ru.skuptsov.algorythms.graph.presentation.Edge;
import ru.skuptsov.algorythms.graph.presentation.Vertex;
import ru.skuptsov.algorythms.graph.presentation.WeightedEdge;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public interface ShortestPath {

    double distTo(Vertex v);

    boolean hasPathTo(Vertex v);

    Iterable<Edge> pathTo(Vertex v);

}
