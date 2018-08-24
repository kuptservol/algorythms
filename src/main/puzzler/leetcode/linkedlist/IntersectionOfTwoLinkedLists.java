package puzzler.leetcode.linkedlist;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Write a program to find the node at which the intersection of two singly linked lists begins.
 */
public class IntersectionOfTwoLinkedLists {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {ListNode.of(1, 2, 3, 4, 5), ListNode.of(6, 7, 8, 9, 11, 3, 4, 5), ListNode.of(3, 4, 5)},
                {ListNode.of(5), ListNode.of(6, 7, 8, 9, 11, 5), ListNode.of(5)},
                {ListNode.of(18, 22, 33), ListNode.of(6, 7, 8, 9, 11, 5), null},
                {ListNode.of(18, 22, 33), ListNode.of(18, 22, 33), ListNode.of(18, 22, 33)},
                {ListNode.of(1, 3, 5, 6, 7, 8, 9, 10), ListNode.of(2, 4, 6, 7, 8, 9, 10), ListNode.of(6, 7, 8, 9, 10)},
        };
    }

    @Test(dataProvider = "testData")
    public void test(ListNode headA, ListNode headB, ListNode intersection) {
        assertEquals(getIntersectionNode(headA, headB), intersection);
    }

    /**
     * at first know length of both lists then inline them one to another from th end and go from shorter
     * one until the end waiting for similar elements, restarting if elements are differ
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode intersectionHead = null;

        int lengthA = getLength(headA);
        int lengthB = getLength(headB);

        ListNode longHead = lengthA > lengthB
                ? headA
                : headB;

        ListNode shortHead = lengthA > lengthB
                ? headB
                : headA;

        //scroll longest to short size
        for (int i = 0; i != Math.max(lengthA, lengthB) - Math.min(lengthA, lengthB); i++) {
            longHead = longHead.next;
        }

        while (longHead != null) {
            if (longHead.val == shortHead.val) {
                if (intersectionHead == null) {
                    intersectionHead = longHead;
                }
            } else {
                intersectionHead = null;
            }

            longHead = longHead.next;
            shortHead = shortHead.next;
        }


        return intersectionHead;
    }

    private int getLength(ListNode head) {
        int length = 0;

        while (head != null) {
            head = head.next;
            length++;
        }

        return length;
    }
}
