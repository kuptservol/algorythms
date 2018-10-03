package puzzler.leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 */
public class SymmetricTree {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {TreeNode.of(1, 2, 2, 3, 4, 4, 3), true},
                {TreeNode.of(1, 2, 2, null, 3, null, 3), false},
                {TreeNode.of(1, 2, 2, 3, null, null, 3), true},
                {TreeNode.of(1, 2, 2, 3, null, null, 3, 4, null, null, 1, 1, null, null, 4), false},
                {TreeNode.of(1, 2, 2, 3, null, null, 3, null, null, null, 1, 1, null, null, 4), false},
                {TreeNode.of(2, 3, 3, 4, 5, 5, 4, null, null, 8, 9, null, null, 9, 8), false},
        };
    }

    @Test(dataProvider = "testData")
    public void test(TreeNode node, boolean result) {
        TreeNode.print(node);
        assertEquals(isSymmetric(node), result);
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

//        return isSymmetricWithLvlSymmCntrl(root);
        return isSymmetricRunnersCtrl(root);
    }

    /**
     * Run with two runners to left and right subtree simmetrically - check on each step if there paths element are similar
     * O(N)
     * mem O(1)
     */
    private boolean isSymmetricRunnersCtrl(TreeNode root) {
        LinkedList<TreeNode> leftBfs = new LinkedList<>();
        LinkedList<TreeNode> rightBfs = new LinkedList<>();

        leftBfs.addLast(root.left);
        rightBfs.addLast(root.right);

        while (leftBfs.size() != 0 && rightBfs.size() != 0) {
            TreeNode leftNode = leftBfs.pollFirst();
            TreeNode rightNode = rightBfs.pollFirst();

            if (leftNode == null && rightNode == null) {
                continue;
            } else {
                if (leftNode != null && rightNode != null) {
                    if (leftNode.val != rightNode.val) {
                        return false;
                    }

                    leftBfs.addLast(leftNode.left);
                    leftBfs.addLast(leftNode.right);

                    rightBfs.addLast(rightNode.right);
                    rightBfs.addLast(rightNode.left);
                } else {
                    return false;
                }
            }
        }

        return leftBfs.size() == 0 && rightBfs.size() == 0;
    }

    /**
     * Do a bfs - level by level - checking that each level is symmetric
     * O(3*N)
     * mem O(maxRowLength)
     * <p>
     * Memory limit exceed
     */
    private boolean isSymmetricWithLvlSymmCntrl(TreeNode root) {
        List<Integer> row = new ArrayList<>();
        int level = 1;
        LinkedList<TreeNode> bfs = new LinkedList<>();
        bfs.addLast(root);

        int index = 0;
        while (!bfs.isEmpty()) {
            TreeNode node = bfs.pollFirst();
            row.add(node == NULL_NODE ? null : node.val);
            index++;

            if (index == level) {
                if (!isSymmetric(row)) {
                    return false;
                }

                if (isAllNulls(row)) {
                    return true;
                }

                level <<= 1;
                index = 0;
                row = new ArrayList<>();
            }

            bfs.add(node.left == null ? NULL_NODE : node.left);
            bfs.add(node.right == null ? NULL_NODE : node.right);
        }

        return true;
    }

    private boolean isAllNulls(List<Integer> row) {
        for (Integer aRow : row) {
            if (aRow != null) {
                return false;
            }
        }
        return true;
    }

    private final static TreeNode NULL_NODE = new TreeNode(0);

    private boolean isSymmetric(List<Integer> row) {
        if (row.size() == 1) {
            return true;
        }

        for (int i = row.size() / 2 - 1; i >= 0; i--) {
            if (!Objects.equals(row.get(i), row.get(row.size() - 1 - i)))
                return false;
        }


        return true;
    }
}
