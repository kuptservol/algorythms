package puzzler.interview.leetcode.linkedlist.removeDuplicates;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static puzzler.interview.leetcode.linkedlist.removeDuplicates.RemoveDuplicatesFromSortedLinkedList.ListNode.cons;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 *         <p>
 *         Given 1->1->2, return 1->2.
 *         Given 1->1->2->3->3, return 1->2->3.
 */
public class RemoveDuplicatesFromSortedLinkedList {
    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {
                        cons("1->1->2"), cons("1->2")
                }
        };
    }

    @Test(dataProvider = "testData")
    public void test(ListNode head, ListNode result) {
        assertEquals(deleteDuplicates(head), result);
    }

    public ListNode deleteDuplicates(ListNode head) {
        ListNode prev = head;
        ListNode next = head.next;
        while (next != null) {
            if (prev.val == next.val) {
                prev.next = next.next;
            } else {
                prev = next;
            }

            next = next.next;
        }

        return head;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        static ListNode cons(String x) {
            String[] split = x.split("->");
            ListNode listNode = new ListNode(Integer.valueOf(split[0]));

            ListNode prev = listNode;
            for (int i = 1; i < split.length; i++) {
                int next = Integer.valueOf(split[i]);
                prev.next = new ListNode(next);
                prev = prev.next;
            }

            return listNode;
        }

        @Override
        public String toString() {
            String s = "";
            while (next != null) {
                s += val + "->";
                next = next.next;
            }
            return s;
        }

        @Override
        public boolean equals(Object obj) {
            ListNode next = (ListNode) obj;
            if (val != next.val) {
                return false;
            }

            if (next != null && next.next != null) {
                return next.equals((next.next));
            } else if (next == null && next.next == null) {
                return true;
            } else {
                return false;
            }

        }
    }
}
