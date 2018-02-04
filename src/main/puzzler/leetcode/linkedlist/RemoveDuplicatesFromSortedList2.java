package puzzler.leetcode.linkedlist;

import java.util.Objects;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         <p>
 *         Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.
 *         <p>
 *         For example, given 1->1->1->2->3, return 2->3.
 */
public class RemoveDuplicatesFromSortedList2 {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {ListNode.of(1, 1, 1, 2, 3), ListNode.of(2, 3)},
                {ListNode.of(1, 2, 2, 3), ListNode.of(1, 3)},
                {ListNode.of(1, 2, 3, 3), ListNode.of(1, 2)},
                {ListNode.of(1), ListNode.of(1)},
                {ListNode.of(1, 2), ListNode.of(1, 2)},
                {ListNode.of(1, 1, 1), ListNode.of()}
        };
    }

    @Test(dataProvider = "testData")
    public void test(ListNode in, ListNode test) {
        assertEquals(deleteDuplicates(in), test);
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }

        Integer prevDuplicateVal = null;

        ListNode next;
        ListNode curr = head;
        ListNode prev = null;
        while ((next = curr.next) != null) {
            if (next.val == curr.val) {
                prevDuplicateVal = next.val;
                head = removeCurrEl(head, next, curr, prev);
            } else if (Objects.equals(curr.val, prevDuplicateVal)) {
                prevDuplicateVal = null;
                head = removeCurrEl(head, next, curr, prev);
            } else {
                prev = curr;
            }

            curr = next;
        }

        if (Objects.equals(curr.val, prevDuplicateVal)) {
            head = removeCurrEl(head, next, curr, prev);
        }

        return head;
    }

    private ListNode removeCurrEl(ListNode head, ListNode next, ListNode curr, ListNode prev) {
        if (curr == head) {
            head = next;
        } else {
            if (prev != null) {
                prev.next = next;
            }
        }
        return head;
    }
}
