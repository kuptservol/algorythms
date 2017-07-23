package puzzler.leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.tree.TreeNode.of;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 * Given a binary tree, find the length of the longest consecutive sequence path.
 * <p>
 * The path refers to any sequence of nodes from some starting node to any node in the tree along
 * the parent-child connections. The longest consecutive path need to be from parent to child (cannot be the reverse).
 */
public class BinaryTreeLongestConsecutiveSequence {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {of(1).right(of(3).left(of(2)).right(of(4).right(of(5)))), 3},
                {of(2).right(of(3).left(of(2).left(of(1)))), 2},
        };
    }

    @Test(dataProvider = "testData")
    public void test(TreeNode root, int b) {
        assertEquals(longestConsecutive(root), b);
    }

    /**
     * Accepted solution
     * Simple cycle dfs with recursion params simulation in class holder
     */
    public int longestConsecutive(TreeNode root) {
        if (root == null) {
            return 0;
        }

        List<Integer> maxHolder = new ArrayList<>(1);
        maxHolder.add(0, 1);

        LinkedList<ExtendedTreeNode> dfs = new LinkedList<>();
        ExtendedTreeNode rootEx = new ExtendedTreeNode(root, maxHolder, root.val + 1, 1);
        dfs.add(rootEx);

        ExtendedTreeNode next;
        while ((next = dfs.pollLast()) != null) {
            int nextVal = next.treeNode.val + 1;
            int nextRecursiveLength = next.currRecursiveLength + 1;

            if (next.nextExpectedValue != next.treeNode.val) {
                nextRecursiveLength = 2;
            } else {
                maxHolder.set(0, Math.max(maxHolder.get(0), next.currRecursiveLength));
            }

            if (next.treeNode.left != null) {
                dfs.push(new ExtendedTreeNode(next.treeNode.left, maxHolder, nextVal, nextRecursiveLength));
            }

            if (next.treeNode.right != null) {
                dfs.push(new ExtendedTreeNode(next.treeNode.right, maxHolder, nextVal, nextRecursiveLength));
            }
        }

        return maxHolder.get(0);
    }

    private static class ExtendedTreeNode {

        private final List<Integer> maxHolder;
        private final int nextExpectedValue;
        private final int currRecursiveLength;
        private final TreeNode treeNode;

        ExtendedTreeNode(TreeNode treeNode, List<Integer> maxHolder, int nextExpectedValue, int currRecursiveLength) {
            this.treeNode = treeNode;
            this.maxHolder = maxHolder;
            this.nextExpectedValue = nextExpectedValue;
            this.currRecursiveLength = currRecursiveLength;
        }
    }

    /*
     with DFS recursion solution - fails on SOF
    * */
    public int longestConsecutiveREcursiveDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        List<Integer> maxHolder = new ArrayList<>(1);
        maxHolder.add(0, 1);

        // with DFS recursion solution - fails on SOF
        if (root.left != null)
            dfs(root.left, maxHolder, root.val++, 2);

        if (root.right != null)
            dfs(root.right, maxHolder, root.val++, 2);


        return maxHolder.get(0);
    }

    private void dfs(TreeNode node, List<Integer> maxHolder, int nextExpectedValue, int currRecursiveLength) {
        int nextVal = node.val + 1;
        int nextRecursiveLength = currRecursiveLength + 1;

        if (nextExpectedValue != node.val) {
            nextRecursiveLength = 2;
        }

        maxHolder.set(0, Math.max(maxHolder.get(0), currRecursiveLength));

        if (node.left != null) {
            dfs(node.left, maxHolder, nextVal, nextRecursiveLength);
        }

        if (node.right != null) {
            dfs(node.right, maxHolder, nextVal, nextRecursiveLength);
        }
    }
}
