package puzzler.leetcode.tree;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Sergey Kuptsov
 *         Given a non-empty binary tree, find the maximum path sum.
 *         <p>
 *         For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along the parent-child connections.
 *         The path must contain at least one node and does not need to go through the root.
 */
public class BinaryTreeMaximumPathSum {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {TreeNode.of(1, 2, 3), 6},
                {TreeNode.of(-10, 9, 20, null, null, 15, 7), 42},
                {TreeNode.of(-10, 9, 20, null, null, 15, -1), 35},
                {TreeNode.of(-10, 9, 20, null, null, 15, -1, 4, -1), 39},
                {TreeNode.of(-10, 9, 20, null, null, 15, -1, 4, -1, 8, null), 46},
                {TreeNode.of(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1), 55}
        };
    }

    @Test(dataProvider = "testData")
    public void test(TreeNode root, int sum) {
        TreeNode.print(root);
        Assert.assertEquals(maxPathSum(root), sum);
    }

    public int maxPathSum(TreeNode root) {
        if (root == null) {
            throw new IllegalArgumentException();
        }
        if (root.right == null && root.left == null) {
            return root.val;
        }

        int[] max = new int[1];
        max[0] = root.val;

        findMaxDfsBottomUp(root, max);

        return max[0];
    }

    private int findMaxDfsBottomUp(TreeNode root, int[] max) {
        Integer leftMax = null;
        if (root.left != null) {
            leftMax = findMaxDfsBottomUp(root.left, max);
        }

        Integer rightMax = null;
        if (root.right != null) {
            rightMax = findMaxDfsBottomUp(root.right, max);
        }

        Integer currMax = null;
        if (leftMax == null && rightMax == null) {
            currMax = root.val;
        }

        if (leftMax != null && rightMax == null) {
            currMax = Math.max(leftMax + root.val, root.val);
        }

        if (leftMax == null && rightMax != null) {
            currMax = Math.max(rightMax + root.val, root.val);
        }

        if (leftMax != null && rightMax != null) {

            Integer currMax1 = Math.max(rightMax + root.val, root.val);
            Integer currMax2 = Math.max(leftMax + root.val, root.val);
            Integer currMax3 = Math.max(leftMax + rightMax + root.val, root.val);

            currMax = Math.max(currMax1, currMax2);
            currMax = Math.max(currMax3, currMax);
        }

        max[0] = Math.max(max[0], currMax);

        return currMax;
    }
}
