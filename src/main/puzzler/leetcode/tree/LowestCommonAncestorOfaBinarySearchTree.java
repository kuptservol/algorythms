package puzzler.leetcode.tree;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
 *
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node
 * in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 *
 * All of the nodes' values will be unique.
 * p and q are different and both values will exist in the BST.
 */
public class LowestCommonAncestorOfaBinarySearchTree {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {TreeNode.of(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5), TreeNode.of(2), TreeNode.of(8), TreeNode.of(6)},
                {TreeNode.of(6, 2, 8, 0, 4, 7, 9, null, null, 3, 5), TreeNode.of(2), TreeNode.of(4), TreeNode.of(2)},
        };
    }

    @Test(dataProvider = "testData")
    public void test(TreeNode root, TreeNode p, TreeNode q, TreeNode res) {
        TreeNode.print(root);
        assertEquals(lowestCommonAncestor(root, p, q), res);
        assertEquals(lowestCommonAncestorMoreTricky(root, p, q), res);
    }


    /**
     * first try - is to construct binary search path to one of the nodes - then do the same for the other - last common wins
     * PASSED!
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        // binary search for p
        Set<TreeNode> pBSPath = new HashSet<>();

        TreeNode pNext = root;
        while (true) {
            int currVal = pNext.val;
            pBSPath.add(pNext);

            if (currVal == p.val) {
                break;
            } else if (p.val < currVal) {
                pNext = pNext.left;
            } else {
                pNext = pNext.right;
            }
        }

        TreeNode lCANode = null;
        // binary search for q
        TreeNode qNext = root;
        while (true) {
            int currVal = qNext.val;
            if (pBSPath.contains(qNext)) {
                lCANode = qNext;
            }

            if (currVal == q.val) {
                break;
            } else if (q.val < currVal) {
                qNext = qNext.left;
            } else {
                qNext = qNext.right;
            }
        }

        return lCANode;
    }

    /**
     * if the way we are BST are start to divide - thi is our target lCANode
     * PASSED!
     */
    public TreeNode lowestCommonAncestorMoreTricky(TreeNode root, TreeNode p, TreeNode q) {

        TreeNode next = root;
        while (true) {

            if (p.val < next.val && q.val < next.val) {
                next = next.left;
            } else if (p.val > next.val && q.val > next.val) {
                next = next.right;
            } else {
                return next;
            }
        }
    }
}
