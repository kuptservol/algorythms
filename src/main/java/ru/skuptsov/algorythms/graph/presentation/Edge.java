package ru.skuptsov.algorythms.graph.presentation;

import com.google.common.base.Objects;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public class Edge {

    protected Vertex fromV;
    protected Vertex toV;

    public Edge(Vertex fromV, Vertex toV) {
        this.fromV = fromV;
        this.toV = toV;
    }

    public Edge convertDirections() {
        Vertex tmp = fromV;
        fromV = toV;
        toV = fromV;
        return this;
    }

    public Vertex getFromV() {
        return fromV;
    }

    public Vertex getToV() {
        return toV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equal(fromV, edge.fromV) &&
                Objects.equal(toV, edge.toV);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fromV, toV);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("fromV", fromV)
                .add("toV", toV)
                .toString();
    }
}
