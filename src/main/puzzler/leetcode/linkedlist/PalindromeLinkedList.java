package puzzler.leetcode.linkedlist;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * Given a singly linked list, determine if it is a palindrome.
 */
public class PalindromeLinkedList {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {ListNode.of(1, 2), false},
                {ListNode.of(1, 2, 4), false},
                {ListNode.of(1, 2, 3, 1), false},
                {ListNode.of(1, 2, 2, 1), true},
                {ListNode.of(1), true},
                {ListNode.of(1, 2, 1), true},
        };
    }

    @Test(dataProvider = "testData")
    public void test(ListNode head, boolean result) {
        assertEquals(isPalindrome(head), result);
    }

    /**
     * O(n) time O(1) space
     * get len - then turn around n/2 - and synchro check
     */
    public boolean isPalindrome(ListNode head) {
        boolean result = true;
        if (head == null) {
            return result;
        }

        int len = 0;
        ListNode next = head;
        while (next != null) {
            next = next.next;
            len++;
        }

        int half = len / 2;

        ListNode firstHalf = head;
        ListNode secondHalf = head;

        while (half != 0) {
            secondHalf = secondHalf.next;
            half--;
        }

        //turn around half
        ListNode prev = null;
        ListNode curr = secondHalf;
        next = secondHalf.next;

        // 3->2->1 ===> 1->2->3
        // null<-3->2->1

        while (next != null) {
            curr.next = prev;

            prev = curr;
            curr = next;
            next = curr.next;
        }
        curr.next = prev;


        half = len / 2;

        while (half != 0) {
            if (curr.val != firstHalf.val) {
                return false;
            }

            curr = curr.next;
            firstHalf = firstHalf.next;

            half--;
        }

        return result;
    }
}
