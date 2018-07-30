package puzzler.leetcode.tree;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a binary tree, determine if it is height-balanced.
 * <p>
 * For this problem, a height-balanced binary tree is defined as:
 * <p>
 * a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
 *
 * @author Sergey Kuptsov
 */
public class BalancedBinaryTree {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {TreeNode.of(3, 9, 20, null, null, 15, 7), true},
                {TreeNode.of(1, 2, 2, 3, 3, null, null, 4, 4), false},
                {TreeNode.of(1, null, 2, null, 3), false},
                {TreeNode.of(1, 2, 2, 3, null, null, 3, 4, null, null, 4), false}
        };
    }

    @Test(dataProvider = "testData")
    public void test(TreeNode root, boolean result) {
        TreeNode.print(root);
        assertEquals(isBalanced(root), result);
    }

    /**
     * Simple dfs
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null || (root.right == null && root.left == null)) {
            return true;
        }

        boolean[] result = new boolean[1];
        result[0] = true;

        dfs(root, result);

        return result[0];
    }

    private int dfs(TreeNode root, boolean[] result) {
        int leftLength = root.left == null ? 0 : dfs(root.left, result);
        int rightLength = root.right == null ? 0 : dfs(root.right, result);

        if ((leftLength - rightLength) * (leftLength - rightLength) > 1) {
            result[0] = false;
        }

        return Math.max(leftLength, rightLength) + 1;
    }
}
