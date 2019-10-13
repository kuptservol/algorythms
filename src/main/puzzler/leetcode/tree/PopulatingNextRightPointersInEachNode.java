package puzzler.leetcode.tree;

import java.util.LinkedList;
import java.util.Objects;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.gradle.internal.impldep.org.junit.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *
 * You are given a perfect binary tree where all leaves are on the same level,
 * and every parent has two children. The binary tree has the
 * following definition:
 *
 * struct Node {
 * int val;
 * Node *left;
 * Node *right;
 * Node *next;
 * }
 * Populate each next pointer to point to its next right node.
 * If there is no next right node, the next pointer should be set to NULL.
 *
 * Initially, all next pointers are set to NULL.
 */
public class PopulatingNextRightPointersInEachNode {

    @DataProvider
    public Object[][] testData() {
        Node node6 = new Node(6, new Node(7));
        Node node3 = new Node(3, node6, new Node(7));

        return new Object[][]{
                {
                        new Node(1,
                                new Node(2, new Node(4, new Node(5)), new Node(5, node6), node3),
                                node3),
                        new Node(1,
                                new Node(2, new Node(4, new Node(5)), new Node(5, node6), node3),
                                node3
                        )
                }
        };
    }

    @Test(dataProvider = "testData")
    public void test(Node root, Node result) {
        Node connect = connect(root);
        assertEquals(connect, result);
    }

    public Node connect(Node root) {
        if (root == null) {
            return null;
        }

        int levelCount = 1;
        int levelCounter = 0;
        Node right = null;
        LinkedList<Node> bfs = new LinkedList<>();
        bfs.addLast(root);

        Node next;
        while ((next = bfs.pollFirst()) != null) {
            if (levelCounter == levelCount) {
                levelCounter = 0;
                right = null;
                levelCount *= 2;
            }

            next.next = right;
            right = next;

            if (next.right != null && next.left != null) {
                bfs.addLast(next.right);
                bfs.addLast(next.left);
            }

            levelCounter++;
        }

        return root;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node(int _val, Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            return val == node.val &&
                    Objects.equals(left, node.left) &&
                    Objects.equals(right, node.right) &&
                    Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val, left, right, next);
        }
    }
}
