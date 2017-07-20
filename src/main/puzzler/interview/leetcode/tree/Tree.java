package puzzler.interview.leetcode.tree;

/**
 * @author Sergey Kuptsov
 * @since 22/11/2016
 */
public class Tree<T> {
    Node<T> root;

    @java.beans.ConstructorProperties({"root"})
    Tree(Node<T> root) {
        this.root = root;
    }

    public static <T> TreeBuilder<T> builder() {
        return new TreeBuilder<T>();
    }

    public Node<T> getRoot() {
        return this.root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Tree)) return false;
        final Tree other = (Tree) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$root = this.getRoot();
        final Object other$root = other.getRoot();
        if (this$root == null ? other$root != null : !this$root.equals(other$root)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $root = this.getRoot();
        result = result * PRIME + ($root == null ? 43 : $root.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Tree;
    }

    public String toString() {
        return "puzzlers.interview.programcreek.tree.traversal.Tree(root=" + this.getRoot() + ")";
    }

    public static class TreeBuilder<T> {
        private Node<T> root;

        TreeBuilder() {
        }

        public Tree.TreeBuilder<T> root(Node<T> root) {
            this.root = root;
            return this;
        }

        public Tree<T> build() {
            return new Tree<T>(root);
        }

        public String toString() {
            return "puzzlers.interview.programcreek.tree.traversal.Tree.TreeBuilder(root=" + this.root + ")";
        }
    }
}
