package puzzler.leetcode.tree;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import puzzler.leetcode.linkedlist.ListNode;

import static org.testng.Assert.assertEquals;

/**
 * Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.
 * <p>
 * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees
 * of every node never differ by more than 1.
 */
public class ConvertSortedListToBinarySearchTree {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {TreeNode.of(0, -3, 9, -10, null, 5), ListNode.of(-10, -3, 0, 5, 9)}
        };
    }

    @Test(dataProvider = "testData")
    public void test(TreeNode result, ListNode input) {
        System.out.println("Answer: ");
        TreeNode.print(result);

//        TreeNode actual = sortedListToBSTToArray(input);
        TreeNode actual = sortedListToBSTBottomUp(input);
        System.out.println("Result: ");
        TreeNode.print(actual);

        assertEquals(actual, result);
    }

    private static ListNode head;

    public TreeNode sortedListToBSTBottomUp(ListNode head) {
        this.head = head;
        return sortedListToBSTBottomUp(0, getLength(head) - 1);
    }

    private TreeNode sortedListToBSTBottomUp(int from, int to) {
        if (to < from) {
            return null;
        }

        int mid = Long.valueOf(Math.round(from + (to - from) / 2d)).intValue();
        TreeNode left = sortedListToBSTBottomUp(from, mid - 1);
        TreeNode root = new TreeNode(head.val);
        root.left = left;
        head = head.next;
        root.right = sortedListToBSTBottomUp(mid + 1, to);

        return root;
    }

    private int getLength(ListNode n) {
        int l = 0;
        while (n != null) {
            l++;
            n = n.next;
        }

        return l;
    }

    // cheaty
    public TreeNode sortedListToBSTToArray(ListNode head) {
        if (head == null)
            return null;

        List<Integer> ints = new ArrayList<>();
        while (head != null) {
            ints.add(head.val);
            head = head.next;
        }

        return sortedArrayToBSTTOArray(ints.toArray(new Integer[0]));
    }

    public TreeNode sortedArrayToBSTTOArray(Integer[] nums) {
        if (nums.length == 0) {
            return null;
        }

        return sortedArrayToBSTTOArray(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBSTTOArray(Integer[] nums, int from, int to) {
        if (to < from) {
            return null;
        }
        int mid = Long.valueOf(Math.round(from + (to - from) / 2d)).intValue();

        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBSTTOArray(nums, from, mid - 1);
        root.right = sortedArrayToBSTTOArray(nums, mid + 1, to);

        return root;
    }
}
