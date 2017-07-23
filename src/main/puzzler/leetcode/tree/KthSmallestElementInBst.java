package puzzler.leetcode.tree;

import java.util.LinkedList;

/**
 * @author Sergey Kuptsov
 * @since 04/03/2017
 * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
 * (1 ≤ k ≤ BST's total elements)
 */
public class KthSmallestElementInBst {

    public static void main(String[] args) {
        KthSmallestElementInBst kthSmallestElementInBst = new KthSmallestElementInBst();

        TreeNode n100 = new TreeNode(100);
        TreeNode n50 = new TreeNode(50);
        TreeNode n1 = new TreeNode(1);
        TreeNode n60 = new TreeNode(60);
        TreeNode n59 = new TreeNode(59);
        TreeNode n65 = new TreeNode(65);
        TreeNode n64 = new TreeNode(64);
        TreeNode n66 = new TreeNode(66);
        TreeNode n58 = new TreeNode(58);
        TreeNode n56 = new TreeNode(56);
        TreeNode n57 = new TreeNode(57);

        n100.left = n50;
        n50.left = n1;
        n50.right = n60;
        n60.left = n59;
        n59.left = n58;
        n58.left = n56;
        n56.right = n57;
        n60.right = n65;
        n65.left = n64;
        n65.right = n66;

        kthSmallestElementInBst.kthSmallest(n100, 12);
    }

    public int kthSmallest(TreeNode root, int k) {
        int counter = 0;

        MarkableTreeNode markableRoot = new MarkableTreeNode(root);
        MarkableTreeNode kth = markableRoot;
        MarkableTreeNode next;

        LinkedList<MarkableTreeNode> nodes = new LinkedList<>();
        nodes.push(markableRoot);

        while (counter < k && (next = nodes.peek()) != null) {
            if (next.marked) {
                counter++;
                kth = next;
                nodes.pop();
                System.out.println(next.val);
                continue;
            }

            if (next.right != null) {
                nodes.pop();// next
                nodes.push(next.right); //set right behind
                nodes.push(next);
            }

            if (next.left != null) {
                nodes.push(next.left);
            }

            next.marked = true;
        }

        return kth.val;
    }

    private static class MarkableTreeNode extends TreeNode {

        boolean marked;
        MarkableTreeNode left;
        MarkableTreeNode right;

        MarkableTreeNode(TreeNode treeNode) {
            super(treeNode.val);
            this.left = treeNode.left == null ? null : new MarkableTreeNode(treeNode.left);
            this.right = treeNode.right == null ? null : new MarkableTreeNode(treeNode.right);
        }
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
