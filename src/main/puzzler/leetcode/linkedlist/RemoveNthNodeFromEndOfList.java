package puzzler.leetcode.linkedlist;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *
 * Given a linked list, remove the n-th node from the end of list and return its head.
 *
 * Example:
 *
 * Given linked list: 1->2->3->4->5, and n = 2.
 *
 * After removing the second node from the end, the linked list becomes 1->2->3->5.
 */
public class RemoveNthNodeFromEndOfList {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {ListNode.of(1, 2, 3, 4, 5), 2, ListNode.of(1, 2, 3, 5)},
                {ListNode.of(1, 2, 3, 4, 5), 5, ListNode.of(2, 3, 4, 5)},
                {ListNode.of(1), 1, null},
                {ListNode.of(1, 2, 3, 4, 5), 1, ListNode.of(1, 2, 3, 4)}
        };
    }

    @Test(dataProvider = "testData")
    public void test(ListNode head, int n, ListNode res) {
        assertEquals(removeNthFromEnd(head, n), res);
    }

    /**
     * simple O(2*N) pass - first - count length - second - remove node
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }

        int length = 0;
        ListNode next = head;
        while (next != null) {
            length++;

            next = next.next;
        }

        if (n > length) {
            return null;
        }

        int index = length - n;

        if (index == 0) {
            return head.next;
        }

        ListNode prevDel = head;
        while ((index -= 1) != 0) {
            prevDel = prevDel.next;
        }

        prevDel.next = prevDel.next.next;

        return head;
    }
}
