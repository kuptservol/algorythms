package puzzler.leetcode.linkedlist;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * @author Sergey Kuptsov
 * Reverse a linked list from position m to n. Do it in one-pass.
 *
 * Note: 1 ≤ m ≤ n ≤ length of list.
 *
 * Example:
 *
 * Input: 1->2->3->4->5->NULL, m = 2, n = 4
 * Output: 1->4->3->2->5->NULL
 */
public class ReverseLinkedListII {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {ListNode.of(1, 2, 3, 4, 5), 2, 4, ListNode.of(1, 4, 3, 2, 5)},
                {ListNode.of(1, 2, 3, 4, 5), 1, 5, ListNode.of(5, 4, 3, 2, 1)},
                {ListNode.of(1, 2, 3, 4, 5), 5, 5, ListNode.of(1, 2, 3, 4, 5)}
        };
    }

    @Test(dataProvider = "testData")
    public void test(ListNode input, int m, int n, ListNode res) {
        assertEquals(res, reverseBetween(input, m, n));
    }

    // remember m-th not reversed node - reverse others till n-th - link new reversed head to m-th
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null) {
            return head;
        }

        ListNode mM1ThNode = null;
        for (int i = 1; i < m; i++) {
            if (mM1ThNode == null) {
                mM1ThNode = head;
            } else {
                mM1ThNode = mM1ThNode.next;
            }
        }

        ListNode prev = null;
        ListNode curr = mM1ThNode == null ? head : mM1ThNode.next;
        ListNode subListStart = curr;
        ListNode next = curr.next;

        for (int i = m; i <= n; i++) {
            curr.next = prev;
            prev = curr;
            curr = next;
            next = next == null ? next : next.next;
        }

        if (mM1ThNode != null) {
            mM1ThNode.next = prev;
        }

        subListStart.next = curr;

        if (mM1ThNode != null) {
            return head;
        } else {
            return prev;
        }
    }

}
