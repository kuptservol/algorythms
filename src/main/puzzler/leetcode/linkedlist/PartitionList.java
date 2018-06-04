package puzzler.leetcode.linkedlist;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.linkedlist.ListNode.of;

/**
 * @author Sergey Kuptsov
 *         Given a linked list and a value x, partition it such that all nodes less
 *         than x come before nodes greater than or equal to x.
 *         <p>
 *         You should preserve the original relative order of the nodes in each of the two partitions.
 *         <p>
 *         Example:
 *         <p>
 *         Input: head = 1->4->3->2->5->2, x = 3
 *         Output: 1->2->2->4->3->5
 */
public class PartitionList {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {of(1, 4, 3, 2, 5, 2), 3, of(1, 2, 2, 4, 3, 5)},
                {of(1, 4, 0), 0, of(1, 4, 0)},
                {of(1, 4, 0), 6, of(1, 4, 0)},
        };
    }

    @Test(dataProvider = "testData")
    public void test(ListNode head, int x, ListNode res) {
        assertEquals(partition(head, x), res);
    }

    public ListNode partition(ListNode head, int x) {
        ListNode firstPartHead = null;
        ListNode firstPartTail = null;
        ListNode secondPartHead = null;
        ListNode secondPartTail = null;

        ListNode next = head;

        while (next != null) {
            if (next.val < x) {
                ListNode listNodeNext = new ListNode(next.val);
                if (firstPartHead == null) {
                    firstPartHead = listNodeNext;
                } else {
                    firstPartTail.next = listNodeNext;
                }
                firstPartTail = listNodeNext;
            } else {
                ListNode listNodeNext = new ListNode(next.val);
                if (secondPartHead == null) {
                    secondPartHead = listNodeNext;
                } else {
                    secondPartTail.next = listNodeNext;
                }
                secondPartTail = listNodeNext;
            }

            next = next.next;
        }

        if (firstPartTail != null && secondPartHead != null) {
            firstPartTail.next = secondPartHead;
        }

        return firstPartHead != null ? firstPartHead : secondPartHead;
    }
}
