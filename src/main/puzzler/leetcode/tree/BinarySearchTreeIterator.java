package puzzler.leetcode.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static junit.framework.TestCase.assertTrue;
import static puzzler.leetcode.PuzzlerUtils.toIntArray;

/**
 * @author Sergey Kuptsov
 */
public class BinarySearchTreeIterator {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {TreeNode.of(5, 2, null, 1, 4, 0, null, 3, null, null, null, null, null, null, null),
                        ImmutableList.of(0, 1, 2, 3, 4, 5)}
        };
    }

    @Test(dataProvider = "testData")
    public void test(TreeNode root, List<Integer> correctArray) {

//        BSTIteratorOnSortedList i = new BSTIteratorOnSortedList(root);
        BSTIteratorOnStack i = new BSTIteratorOnStack(root);

        List<Integer> l = new ArrayList<>();
        while (i.hasNext()) {
            l.add(i.next());
        }

        TreeNode.print(root);
        System.out.println(l);

        assertTrue(Arrays.equals(toIntArray(l), toIntArray(correctArray)));
    }

    /**
     * Clean O(height) memory solution
     *
     * Based on BST properties that for each node all left nodes are less than root and all right nodes are bigger
     * so for a current root add all lefts - for each left add all rights between and so on
     */
    public class BSTIteratorOnStack {

        //the smallest element on top of stack
        LinkedList<TreeNode> stack = new LinkedList<>();

        public BSTIteratorOnStack(TreeNode root) {
            // start with the most left element
            addLefts(root);
        }

        /**
         * @return whether we have a next smallest number
         */
        public boolean hasNext() {
            return stack.peek() != null;
        }

        /**
         * @return the next smallest number
         */
        public int next() {
            TreeNode node = stack.poll();
            addLefts(node.right);
            return node.val;
        }

        public void addLefts(TreeNode node) {
            while (node != null) {
                stack.addFirst(node);
                node = node.left;
            }
        }
    }

    /**
     * Actually do not meet initial memory requirments but passes
     * O(1) O(N)
     */
    public class BSTIteratorOnSortedList {
        private final Iterator<Integer> iterator;
        List<Integer> l = new ArrayList<>();

        public BSTIteratorOnSortedList(TreeNode root) {

            dfs(root, l);
            Collections.sort(l);

            iterator = l.iterator();
        }

        /**
         * @return whether we have a next smallest number
         */
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /**
         * @return the next smallest number
         */
        public int next() {
            return iterator.next();
        }

        private void dfs(TreeNode node, List<Integer> l) {
            if (node != null) {
                l.add(node.val);

                dfs(node.left, l);
                dfs(node.right, l);
            }
        }
    }
}
