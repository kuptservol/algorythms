package ru.skuptsov.algorythms.graph.presentation;

import com.google.common.base.Objects;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public class Vertex<P> {

    private final P value;

    public Vertex(P value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equal(value, vertex.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("value", value)
                .toString();
    }
}
