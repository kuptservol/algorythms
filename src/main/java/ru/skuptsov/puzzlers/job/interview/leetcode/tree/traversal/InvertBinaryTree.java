package ru.skuptsov.puzzlers.job.interview.leetcode.tree.traversal;

import java.util.LinkedList;

/**
 * @author Sergey Kuptsov
 * @since 24/12/2016
 * Google: 90% of our engineers use the software you wrote (Homebrew),
 * but you can’t invert a binary tree on a whiteboard so fuck off.
 */
public class InvertBinaryTree {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);

        InvertBinaryTree invertBinaryTree = new InvertBinaryTree();
        invertBinaryTree.invertTree(root);

    }

    public TreeNode invertTree(TreeNode root) {
        LinkedList<TreeNode> nodesQueue = new LinkedList<>();
        nodesQueue.add(root);

        TreeNode next, tmp;
        while ((next = nodesQueue.poll()) != null) {
            tmp = next.right;
            next.right = next.left;
            next.left = tmp;
            if (next.right != null)
                nodesQueue.add(next.right);
            if (next.left != null)
                nodesQueue.add(next.left);
        }

        return root;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
