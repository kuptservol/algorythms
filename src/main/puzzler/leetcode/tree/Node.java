package puzzler.leetcode.tree;

import com.google.common.base.Objects;

/**
 * @author Sergey Kuptsov
 * @since 22/11/2016
 */
public class Node<T> {
    T value;
    Node<T> left;
    Node<T> right;

    @java.beans.ConstructorProperties({"value", "left", "right"})
    Node(T value, Node<T> left, Node<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public static <T> NodeBuilder<T> builder() {
        return new NodeBuilder<T>();
    }

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

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLeft() {
        return this.left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return this.right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public String toString() {
        return "puzzlers.interview.programcreek.tree.traversal.Node(value=" + this.getValue() + ", left=" + this.getLeft() + ", right=" + this.getRight() + ")";
    }

    public static class NodeBuilder<T> {
        private T value;
        private Node<T> left;
        private Node<T> right;

        NodeBuilder() {
        }

        public Node.NodeBuilder<T> value(T value) {
            this.value = value;
            return this;
        }

        public Node.NodeBuilder<T> left(Node<T> left) {
            this.left = left;
            return this;
        }

        public Node.NodeBuilder<T> right(Node<T> right) {
            this.right = right;
            return this;
        }

        public Node<T> build() {
            return new Node<T>(value, left, right);
        }

        public String toString() {
            return "puzzlers.interview.programcreek.tree.traversal.Node.NodeBuilder(value=" + this.value + ", left=" + this.left + ", right=" + this.right + ")";
        }
    }
}
