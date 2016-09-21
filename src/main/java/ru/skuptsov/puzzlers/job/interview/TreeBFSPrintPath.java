package ru.skuptsov.puzzlers.job.interview;

/**
 * @author Sergey Kuptsov
 * @since 20/09/2016
 * Print paths from root to all nodes
 */
public class TreeBFSPrintPath {

    public static void main(String[] args) {
        Node tree = initializeTree();

        System.out.println(tree);

        String path = "";
        goThrough(tree, path);
    }

    private static void goThrough(Node currNode, String currPath) {
        String newCurrPath = currPath + ":" + currNode.getValue();
        System.out.println("Path from root to node:" + currNode.getValue() + " is " + newCurrPath);
        if (currNode.left != null)
            goThrough(currNode.left, newCurrPath);
        if (currNode.right != null)
            goThrough(currNode.right, newCurrPath);
    }

    private static Node initializeTree() {
        return new Node(0,
                new Node(2, new Node(4, null, null), new Node(5, null, null)),
                new Node(3, new Node(6, null, null), new Node(7, new Node(8, null, null), null))
        );
    }

    private final static class Node {
        private Integer value;
        private Node left;
        private Node right;

        public Node(Integer value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Integer getValue() {
            return value;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }
    }
}
