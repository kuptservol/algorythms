package puzzler.leetcode.tree;

public interface PrintableNode {
    /**
     * Get left child
     */
    PrintableNode getLeft();


    /**
     * Get right child
     */
    PrintableNode getRight();


    /**
     * Get text to be printed
     */
    String getText();
}
