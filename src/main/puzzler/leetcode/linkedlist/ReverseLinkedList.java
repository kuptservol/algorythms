package puzzler.leetcode.linkedlist;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 */
public class ReverseLinkedList {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {ListNode.of(1, 2, 3, 4, 5), ListNode.of(5, 4, 3, 2, 1)},
                {ListNode.of(1), ListNode.of(1)}
        };
    }

    @Test(dataProvider = "testData")
    public void test(ListNode input, ListNode res) {
        assertEquals(reverseListIterative(input), res);
//        assertEquals(reverseListRecursive(input), res);
    }

    public ListNode reverseListIterative(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode prev = null;
        ListNode curr = head;
        ListNode next = head.next;

        while (next != null) {
            curr.next = prev;

            prev = curr;
            curr = next;
            next = curr.next;
        }
        curr.next = prev;

        return curr;
    }

    private ListNode reverseListRecursive(ListNode head) {
        if (head == null) {
            return head;
        }

        return reverseRecursive(head, null);

    }

    private ListNode reverseRecursive(ListNode curr, ListNode prev) {
        ListNode next = curr.next;
        curr.next = prev;
        if (next != null) {
            return reverseRecursive(next, curr);
        } else {
            return curr;
        }
    }
}
