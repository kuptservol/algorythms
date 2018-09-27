package puzzler.leetcode.linkedlist;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Remove all elements from a linked list of integers that have value val.
 */
public class RemoveLinkedListElements {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {ListNode.of(1, 2, 3), 2, ListNode.of(1, 3)},
                {ListNode.of(1, 2, 2, 3), 2, ListNode.of(1, 3)},
                {ListNode.of(1, 1, 3), 1, ListNode.of(3)},
                {ListNode.of(1, 2, 3), 1, ListNode.of(2, 3)},
                {ListNode.of(1, 2, 3, 3, 3), 3, ListNode.of(1, 2)},
                {ListNode.of(1, 2, 3, 3, 3, 1), 3, ListNode.of(1, 2, 1)},
                {ListNode.of(1, 2, 3, 3, 3, 1), 1, ListNode.of(2, 3, 3, 3)},
        };
    }

    @Test(dataProvider = "testData")
    public void test(ListNode head, int val, ListNode res) {
        assertEquals(res, removeElements(head, val));
    }

    public ListNode removeElements(ListNode head, int val) {

        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            if (curr.val == val) {
                if (prev == null) {
                    head = curr.next;
                } else {
                    prev.next = curr.next;

                }
            } else {
                prev = curr;
            }

            curr = curr.next;
        }

        return head;
    }
}
