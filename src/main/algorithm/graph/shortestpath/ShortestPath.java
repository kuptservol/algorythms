package algorithm.graph.shortestpath;

import algorithm.structure.graph.Edge;
import algorithm.structure.graph.Vertex;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public interface ShortestPath {

    double distTo(Vertex v);

    boolean hasPathTo(Vertex v);

    Iterable<Edge> pathTo(Vertex v);
}
