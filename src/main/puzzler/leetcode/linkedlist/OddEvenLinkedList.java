package puzzler.leetcode.linkedlist;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         <p>
 *         Given a singly linked list, group all odd nodes together followed by the even nodes. Please note here we are talking about the node number and not the value in the nodes.
 *         <p>
 *         You should try to do it in place. The program should run in O(1) space complexity and O(nodes) time complexity.
 *         <p>
 *         Example:
 *         Given 1->2->3->4->5->NULL,
 *         return 1->3->5->2->4->NULL.
 *         <p>
 *         Note:
 *         The relative order inside both the even and odd groups should remain as it was in the input.
 *         The first node is considered odd, the second node even and so on ...
 */
public class OddEvenLinkedList {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {ListNode.of(1, 2, 3, 4, 5), ListNode.of(1, 3, 5, 2, 4)},
                {ListNode.of(1, 2, 3, 4, 5, 6, 7), ListNode.of(1, 3, 5, 7, 2, 4, 6)},
                {ListNode.of(1, 2, 3, 4, 5, 6), ListNode.of(1, 3, 5, 2, 4, 6)}
        };
    }

    @Test(dataProvider = "testData")
    public void test(ListNode input, ListNode expected) {

        ListNode result = oddEvenList(input);
        System.out.println("Expected: " + expected);
        System.out.println("Result:   " + result);
        assertEquals(expected, result);
    }

    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return null;
        }

        if (head.next == null || head.next.next == null) {
            return head;
        }

        ListNode headOdd = head;
        ListNode nextOdd = headOdd;
        ListNode headEven = head.next;
        ListNode nextEven = headEven;

        while (nextEven != null) {
            if (nextOdd.next != null && nextOdd.next.next != null) {
                nextOdd.next = nextOdd.next.next;
                nextOdd = nextOdd.next;
            }

            if (nextEven.next != null) {
                nextEven.next = nextEven.next.next;
            }
            nextEven = nextEven.next;
        }

        nextOdd.next = headEven;

        return headOdd;
    }
}
