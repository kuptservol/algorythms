package puzzler.leetcode.stringarray;

import java.util.Arrays;
import java.util.Comparator;

import com.google.common.collect.MinMaxPriorityQueue;

/**
 * @author Sergey Kuptsov
 * @since 27/09/2016
 * Find the kth largest element in an unsorted array.
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 * <p>
 * For example, given [3,2,1,5,6,4] and k = 2, return 5.
 * <p>
 * Note: You may assume k is always valid, 1 ≤ k ≤ array's length.
 */
public class KthGreatestElement {

    private final static int[] given = {3, 2, 1, 5, 6, 4};
    private final static int k = 2;
    private final static int wrightAnswer = 5;

    public static void main(String[] args) {
        System.out.println(findKthLargestElement(given, k) == wrightAnswer);
        System.out.println(findKthLargestElementWithSort(given, k) == wrightAnswer);
        System.out.println(findKthLargestElementWithHeap(given, k) == wrightAnswer);
        System.out.println(findKthLargestWithQuickSelect(given, k) == wrightAnswer);
    }

    /**
     * O(N*k)
     */
    private static int findKthLargestElement(int[] given, int k) {
        int[] kLargest = new int[k];
        for (int i = 0; i < given.length; i++) {
            int gNext = given[i];
            for (int j = k - 1; j >= 0; j--) {
                if (gNext >= kLargest[j]) {
                    int kLargestToPullDown = kLargest[j];
                    kLargest[j] = gNext;

                    for (int l = j - 1; l >= 0; l--) {
                        int inter = kLargest[l];
                        kLargest[l] = kLargestToPullDown;
                        kLargestToPullDown = inter;
                    }

                    break;
                }
            }
        }

        return kLargest[0];
    }

    /**
     * O(Nlogk)
     */
    private static int findKthLargestElementWithHeap(int[] given, int k) {
        MinMaxPriorityQueue<Integer> heap = MinMaxPriorityQueue
                .orderedBy(Comparator.comparingInt(value -> (int) value).reversed())
                .maximumSize(k)
                .create();

        for (int i = 0; i < given.length; i++) {
            heap.add(given[i]);
        }

        for (int i = 0; i < k - 1; i++) {
            heap.poll();
        }

        return heap.poll();
    }

    /**
     * O(NlogN)
     */
    private static int findKthLargestElementWithSort(int[] given, int k) {
        Arrays.sort(given);

        return given[given.length - k];
    }

    /**
     * O(N)
     */
    private static int findKthLargestWithQuickSelect(int[] given, int k) {
        return quickSelect(given, 0, given.length - 1);
    }

    private static int quickSelect(int[] a, int lo, int hi) {
        int pivot = partition(given, lo, hi);

        int findingPosition = given.length - k;
        if (pivot == findingPosition) {
            return given[pivot];
        } else if (pivot > findingPosition) {
            return quickSelect(given, lo, pivot - 1);
        } else {
            return quickSelect(given, pivot + 1, hi);
        }
    }

    private static int partition(int[] a, int lo, int hi) {
        int pivot = a[lo];
        int left = lo;
        int right = hi + 1;

        while (true) {
            while (a[++left] < pivot) if (left == hi) break;
            while (a[--right] > pivot) if (right == lo) break;

            if (left >= right) break;
            int exch = a[left];
            a[left] = a[right];
            a[right] = exch;
        }

        int exch = a[right];
        a[right] = a[lo];
        a[lo] = exch;

        return right;
    }
}
