package ru.skuptsov.puzzlers.job.interview.programcreek.linkedlist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Kuptsov
 * @since 23/12/2016
 * <p>
 * Given a singly linked list L: L0→L1→ ... →Ln-1→Ln,
 * reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→...
 * <p>
 * For example, given {1,2,3,4}, reorder it to {1,4,2,3}.
 * You must do this in-place without altering the nodes' values.
 */
public class ReorderList {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
//        head.next = new ListNode(2);
//        head.next.next = new ListNode(3);
//        head.next.next.next = new ListNode(4);
        ReorderList reorderList = new ReorderList();

        reorderList.reorderListWithArrayIndex(head);
    }

    public void reorderListWithArrayIndex(ListNode head) {
        if (head == null) {
            return;
        }
        List<ListNode> listNodes = new ArrayList<>();

        ListNode next = head;
        while (next != null) {
            listNodes.add(next);
            next = next.next;
        }

        next = head;
        int k = 0;
        for (int i = 1, j = listNodes.size() - 1; i < j; i++, j--) {
            ListNode lN = listNodes.get(j);
            lN.next = null;
            next.next = lN;

            next = next.next;
            ListNode l0 = listNodes.get(i);
            l0.next = null;

            next.next = l0;
            next = next.next;
            k++;
        }

        if (listNodes.size() % 2 == 0) {
            ListNode lN = listNodes.get(listNodes.size() - ++k);
            lN.next = null;
            next.next = lN;
        }

    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
