package puzzler.leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TreeNode implements PrintableNode {
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

    public static TreeNode of(Integer... args) {
        LinkedList<TreeNode> bfs = new LinkedList<>();
        TreeNode root = new TreeNode(args[0]);
        bfs.addFirst(root);
        for (int i = 1; i < args.length; i++) {
            TreeNode next = bfs.pollFirst();
            Integer leftVal = args[i];
            TreeNode left = null;
            TreeNode right = null;
            if (leftVal != null) {
                left = new TreeNode(leftVal);
            }

            if (i + 1 != args.length) {
                i++;
                Integer rightVal = args[i];
                if (rightVal != null) {
                    right = new TreeNode(rightVal);
                }
            }

            if (left != null) {
                bfs.add(left);
                next.left = left;
            }

            if (right != null) {
                bfs.add(right);
                next.right = right;
            }
        }

        return root;
    }

    public static TreeNode of(int val) {
        return new TreeNode(val);
    }

    @Override
    public PrintableNode getLeft() {
        return left;
    }

    @Override
    public PrintableNode getRight() {
        return right;
    }

    @Override
    public String getText() {
        return val + "";
    }

    @Override
    public boolean equals(Object node) {
        return ((TreeNode) node).val == val;
    }

    public static void print(PrintableNode root)
    {
        List<List<String>> lines = new ArrayList<>();

        List<PrintableNode> level = new ArrayList<>();
        List<PrintableNode> next = new ArrayList<>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (PrintableNode n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.getText();
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null) nn++;
                    if (n.getRight() != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<PrintableNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TreeNode{");
        sb.append("val=").append(val);
        sb.append('}');
        return sb.toString();
    }
}
