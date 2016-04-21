package ru.skuptsov.algorythms.graph.presentation.adjacency.list;

import com.google.common.base.Objects;
import ru.skuptsov.algorythms.graph.presentation.AbstractGraph;
import ru.skuptsov.algorythms.graph.presentation.Edge;
import ru.skuptsov.algorythms.graph.presentation.Vertex;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public class AdjacencyMatrixGraphv2 extends AbstractGraph<Vertex, Edge> {

    private final static class VertexMatrixElement {
        private final Vertex vX;
        private final Vertex vY;

        public VertexMatrixElement(Vertex vX, Vertex vY) {
            this.vX = vX;
            this.vY = vY;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VertexMatrixElement that = (VertexMatrixElement) o;
            return Objects.equal(vX, that.vX) &&
                    Objects.equal(vY, that.vY);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(vX, vY);
        }
    }

    private Map<VertexMatrixElement, Edge> adj;

    public AdjacencyMatrixGraphv2(int vNum) {
        super(vNum);
        adj = new HashMap<>(vNum * vNum);
    }

    @Override
    public void addEdge(Edge edge) {
        adj.put(new VertexMatrixElement(edge.getFromV(), edge.getToV()), edge);
        eNum++;
    }

    @Override
    public Iterable<Vertex> adj(Vertex vertex) {
        return adj.get(vertex);
    }
}
