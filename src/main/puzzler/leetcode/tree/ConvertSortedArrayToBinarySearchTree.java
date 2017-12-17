package puzzler.leetcode.tree;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.primitives.Ints.toArray;
import static org.testng.Assert.assertEquals;

/**
 * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
 * <p>
 * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the
 * two subtrees of every node never differ by more than 1.
 */
public class ConvertSortedArrayToBinarySearchTree {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {TreeNode.of(0, -3, 9, -10, null, 5), toArray(of(-10, -3, 0, 5, 9))},
                {TreeNode.of(0), toArray(of(0))}
        };
    }

    @Test(dataProvider = "testData")
    public void test(TreeNode result, int[] input) {
        System.out.println("Answer: ");
        TreeNode.print(result);
        TreeNode actual = sortedArrayToBST(input);
        System.out.println("Result: ");
        TreeNode.print(actual);

        assertEquals(actual, result);
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) {
            return null;
        }

        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBST(int[] nums, int from, int to) {
        if (to < from) {
            return null;
        }
        int mid = Long.valueOf(Math.round(from + (to - from) / 2d)).intValue();

        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, from, mid - 1);
        root.right = sortedArrayToBST(nums, mid + 1, to);

        return root;
    }
}
