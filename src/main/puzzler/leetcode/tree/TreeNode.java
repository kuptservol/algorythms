package puzzler.leetcode.tree;

public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    public TreeNode left(TreeNode left) {
        this.left = left;
        return this;
    }

    public TreeNode left(int val) {
        this.left = of(val);
        return left;
    }

    public TreeNode right(int val) {
        this.right = of(val);
        return right;
    }

    public TreeNode right(TreeNode right) {
        this.right = right;
        return this;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static TreeNode of(int val, TreeNode left, TreeNode right) {
        return new TreeNode(val, left, right);
    }

    public static TreeNode of(int val) {
        return new TreeNode(val);
    }
}
