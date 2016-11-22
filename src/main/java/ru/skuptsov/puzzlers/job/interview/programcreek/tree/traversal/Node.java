package ru.skuptsov.puzzlers.job.interview.programcreek.tree.traversal;

import com.google.common.base.Objects;
import lombok.Builder;
import lombok.Data;

/**
 * @author Sergey Kuptsov
 * @since 22/11/2016
 */
@Builder
@Data
public class Node<T> {
    T value;
    Node<T> left;
    Node<T> right;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equal(value, node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
