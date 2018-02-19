package puzzler.leetcode.tree;

import java.util.LinkedList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Given a binary tree, find its minimum depth.
 *         <p>
 *         The minimum depth is the number of nodes along the shortest path from the
 *         root node down to the nearest leaf node.
 */
public class MinimumDepthOfBinaryTree {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {TreeNode.of(0), 1}
        };
    }

    @Test(dataProvider = "testData")
    public void test(TreeNode in, int minDepth) {
        assertEquals(minDepth(in), minDepth);
    }

    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int depth = Integer.MAX_VALUE;
        LinkedList<TreeNodeWrapper> bfs = new LinkedList<>();
        bfs.add(new TreeNodeWrapper(root, 1));
        TreeNodeWrapper next;
        while ((next = bfs.pollFirst()) != null) {
            if (next.node.left == null && next.node.right == null) {
                depth = Math.min(depth, next.currDepth);
            } else {
                if (next.node.left != null) {
                    bfs.addLast(new TreeNodeWrapper(next.node.left, next.currDepth + 1));
                }

                if (next.node.right != null) {
                    bfs.addLast(new TreeNodeWrapper(next.node.right, next.currDepth + 1));
                }
            }
        }

        return depth;
    }

    private static class TreeNodeWrapper {
        final TreeNode node;
        final int currDepth;


        private TreeNodeWrapper(TreeNode node, int currDepth) {
            this.node = node;
            this.currDepth = currDepth;
        }
    }
}
