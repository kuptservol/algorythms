package puzzler.leetcode.tree;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.tree.TreeNode.of;

/**
 * Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
 * Given the below binary tree and sum = 22,
 * 5
 * / \
 * 4   8
 * /   / \
 * 11  13  4
 * /  \    / \
 * 7    2  5   1
 * return
 * [
 * [5,4,11,2],
 * [5,8,4,5]
 * ]
 */
public class PathSumII {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {
                        of(of(5, 4, 11, 2), of(5, 8, 4, 5)),
                        of(5)
                                .left(of(4).left(of(11).left(of(7)).right(of(2))))
                                .right(of(8).left(of(13)).right(of(4).left(of(5)).right(of(1)))),
                        22
                }
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<List<Integer>> list, TreeNode root, int sum) {
        assertEquals(pathSum(root, sum), list);
    }

    // recursive
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> pathList = new ArrayList<>();

        if (root == null) {
            return pathList;
        }

        List<Integer> currPath = new ArrayList<>();
        currPath.add(root.val);

        dfs(root, pathList, currPath, sum);

        return pathList;
    }

    private void dfs(TreeNode currNode, List<List<Integer>> pathList, List<Integer> currPath, int targetSum) {
        if (currNode.right == null && currNode.left == null &&
                currPath.stream().reduce((a, b) -> a + b).get() == targetSum)
        {
            pathList.add(currPath);
        }

        if (currNode.left != null) {
            List<Integer> newCurrPath = new ArrayList<>(currPath);
            newCurrPath.add(currNode.left.val);
            dfs(currNode.left, pathList, newCurrPath, targetSum);
        }

        if (currNode.right != null) {
            List<Integer> newCurrPath = new ArrayList<>(currPath);
            newCurrPath.add(currNode.right.val);
            dfs(currNode.right, pathList, newCurrPath, targetSum);
        }
    }
}
