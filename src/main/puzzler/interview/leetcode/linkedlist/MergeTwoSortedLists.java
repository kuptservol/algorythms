package puzzler.interview.leetcode.linkedlist;

/**
 * @author Sergey Kuptsov
 * @since 02/03/2017
 * <p>
 * Merge two sorted linked lists and return it as a new list.
 * The new list should be made by splicing together the nodes of the first two lists.
 */
public class MergeTwoSortedLists {

    public static void main(String[] args) {
        MergeTwoSortedLists mergeTwoSortedLists = new MergeTwoSortedLists();
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(5);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(6);
        ListNode l5 = new ListNode(7);

        l1.next = l2;
        l3.next = l4;
        l4.next = l5;

        ListNode listNode = mergeTwoSortedLists.mergeTwoLists(l1, l3);

        ListNode listNode2 = mergeTwoSortedLists.mergeTwoLists(l3, l1);


        System.out.println("");
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode nextL1 = l1;
        ListNode nextL2 = l2;
        ListNode mergeResultFirstNode = null;
        ListNode next;
        ListNode mergeResultNext = null;

        while (nextL1 != null || nextL2 != null) {
            if (nextL1 != null && ((nextL2 != null && nextL1.val <= nextL2.val) || nextL2 == null)) {
                next = new ListNode(nextL1.val);
                nextL1 = nextL1.next;
            } else {
                next = new ListNode(nextL2.val);
                nextL2 = nextL2.next;
            }

            if (mergeResultFirstNode == null) {
                mergeResultFirstNode = next;
                mergeResultNext = next;
            } else {
                mergeResultNext.next = next;
                mergeResultNext = next;
            }
        }

        return mergeResultFirstNode;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
