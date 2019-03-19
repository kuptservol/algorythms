package puzzler.leetcode.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * Given a binary tree, imagine yourself standing on the right side of it,
 * return the values of the nodes you can see ordered from top to bottom
 */
public class BinaryTreeRightSideView {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {TreeNode.of(1, 2, 3, null, 5, null, 4), ImmutableList.of(1, 3, 4)}
        };
    }

    @Test(dataProvider = "testData")
    public void test(TreeNode root, List<Integer> b) {
        TreeNode.print(root);
        assertEquals(rightSideView(root), b);
    }

    /**
     * walk with dfs right subtree always first - so first found node on concrete level is rightmost
     * controlling depth with stack
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> rightMost = new ArrayList<>();

        if (root == null) {
            return rightMost;
        }

        int maxDepth = 0;
        Map<Integer, Integer> nodeOnDepth = new HashMap<>();

        LinkedList<Integer> depthStack = new LinkedList<>();
        LinkedList<TreeNode> dfsStack = new LinkedList<>();

        depthStack.addFirst(1);
        dfsStack.addFirst(root);

        while (!dfsStack.isEmpty()) {
            TreeNode currNode = dfsStack.poll();
            int currDepth = depthStack.poll();

            if (currNode != null) {
                maxDepth = Math.max(currDepth, maxDepth);

                // add if only not exist - cause rightmost was first on curr level
                if (!nodeOnDepth.containsKey(currDepth)) {
                    nodeOnDepth.put(currDepth, currNode.val);
                }

                // for left and right nodes
                depthStack.addFirst(currDepth + 1);
                depthStack.addFirst(currDepth + 1);

                dfsStack.addFirst(currNode.left);
                // right first
                dfsStack.addFirst(currNode.right);
            }
        }

        for (int i = 1; i <= maxDepth; i++) {
            rightMost.add(nodeOnDepth.get(i));
        }

        return rightMost;
    }
}
