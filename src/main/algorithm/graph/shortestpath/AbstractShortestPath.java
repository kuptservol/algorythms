package algorithm.graph.shortestpath;

import algorithm.structure.graph.Graph;
import algorithm.structure.graph.Vertex;
import algorithm.structure.graph.WeightedEdge;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public abstract class AbstractShortestPath<P> implements ShortestPath {

    protected final Vertex<P> fromV;
    protected final Graph<P, Vertex<P>, WeightedEdge<P>> graph;

    public AbstractShortestPath(Graph<P, Vertex<P>, WeightedEdge<P>> graph, Vertex<P> fromV) {
        this.fromV = fromV;
        this.graph = graph;
    }
}
