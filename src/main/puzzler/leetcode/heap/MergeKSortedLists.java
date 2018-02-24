package puzzler.leetcode.heap;

import java.util.PriorityQueue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import puzzler.leetcode.linkedlist.ListNode;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.collect.Iterables.toArray;
import static org.testng.Assert.assertEquals;

/**
 * Merge k sorted linked lists and return it as one sorted list.
 */
public class MergeKSortedLists {
    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {toArray(of(ListNode.of(1, 2, 2)), ListNode.class), ListNode.of(1, 2, 2)},
                {toArray(of(ListNode.of(1, 2, 2), ListNode.of(2, 4), ListNode.of(3)), ListNode.class), ListNode.of(1, 2, 2, 2, 3, 4)},
        };
    }

    @Test(dataProvider = "testData")
    public void test(ListNode[] lists, ListNode merged) {
        assertEquals(mergeKLists(lists), merged);
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        if (lists.length > 50) {
            return heapSorted(lists);
        }

        return getMergeSorted(lists);
    }

    private ListNode heapSorted(ListNode[] lists) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        for (ListNode list : lists) {
            ListNode next = list;
            while (next != null) {
                priorityQueue.add(next.val);
                next = next.next;
            }
        }

        ListNode head = null;
        ListNode next = null;
        Integer nextVal;
        while ((nextVal = priorityQueue.poll()) != null) {
            if (head == null) {
                head = new ListNode(nextVal);
                next = head;
            } else {
                next.next = new ListNode(nextVal);
                next = next.next;
            }
        }

        return head;
    }

    private ListNode getMergeSorted(ListNode[] lists) {
        ListNode head = null;
        ListNode next = null;
        int minIndex;
        while ((minIndex = findMinIndex(lists)) != -1) {
            if (head == null) {
                head = new ListNode(lists[minIndex].val);
                next = head;
            } else {
                next.next = new ListNode(lists[minIndex].val);
                next = next.next;
            }

            lists[minIndex] = lists[minIndex].next;
        }

        return head;
    }

    int findMinIndex(ListNode[] lists) {
        int minIndex = -1;
        int minVal = Integer.MAX_VALUE;

        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                if (lists[i].val < minVal) {
                    minVal = lists[i].val;
                    minIndex = i;
                }
            }
        }

        return minIndex;
    }
}
