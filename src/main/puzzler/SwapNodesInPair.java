package puzzler;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import puzzler.leetcode.linkedlist.ListNode;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *
 * Given a linked list, swap every two adjacent nodes and return its head.
 *
 * Example:
 *
 * Given 1->2->3->4, you should return the list as 2->1->4->3.
 */
public class SwapNodesInPair {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {ListNode.of(1, 2, 3, 4), ListNode.of(2, 1, 4, 3)},
                {ListNode.of(1, 2, 3), ListNode.of(2, 1, 3)},
                {ListNode.of(1), ListNode.of(1)},
        };
    }

    @Test(dataProvider = "testData")
    public void test(ListNode head, ListNode result) {
        assertEquals(swapPairs(head), result);
    }

    public ListNode swapPairs(ListNode head) {
        ListNode nextPairF = head;
        ListNode nextPairS;
        ListNode prevPairS = null;

        while (nextPairF != null) {
            nextPairS = nextPairF.next;
            if (nextPairS != null) {
                nextPairF.next = nextPairS.next;
                nextPairS.next = nextPairF;

                if (prevPairS == null) {
                    //set header
                    head = nextPairS;
                } else {
                    prevPairS.next = nextPairS;
                }

                prevPairS = nextPairF;

                nextPairF = nextPairF.next;
            } else {
                nextPairF = null;
            }
        }

        return head;
    }
}
