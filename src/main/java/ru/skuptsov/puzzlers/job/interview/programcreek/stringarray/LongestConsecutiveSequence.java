package ru.skuptsov.puzzlers.job.interview.programcreek.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by Сергей on 14.12.2016.
 * <p>
 * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
 * For example, given [100, 4, 200, 1, 3, 2], the longest consecutive elements sequence should be [1, 2, 3, 4]. Its length is 4.
 * <p>
 * Your algorithm should run in O(n) complexity.
 */
public class LongestConsecutiveSequence {
    static int[] m1 = {100, 4, 200, 1, 3, 2};
    static int[] m2 = {0, -1};

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {m1, 4}
        };
    }

    public static void main(String[] args) {
        LongestConsecutiveSequence longestConsecutiveSequence = new LongestConsecutiveSequence();
        System.out.println(longestConsecutiveSequence.getLongestConsecutiveSequenceLengthByteIndex(m2));
    }

    @Test(dataProvider = "testData")
    public void test(int[] a, int b) {
        assertEquals(getLongestConsecutiveSequenceLengthByteIndex(a), b);
    }

    public int longestConsecutive(int[] nums) {
        return getLongestConsecutiveSequenceLengthByteIndex(nums);
    }

    /**
     * Memory O(Nmax)
     */
    private int getLongestConsecutiveSequenceLengthByteIndex(int[] massive) {
        if (massive.length == 1) return 1;
        if (massive.length == 0) return 0;

        int max = 0;
        int min = 0;
        for (int j : massive) {
            if (j > max) max = j;
            if (j < min) min = j;
        }

        if (min < 0) {
            min = -min;
        }
        byte[] mask = new byte[min + max + 1];

        for (int j : massive) {
            mask[j + min] = 1;
        }

        int maxSeqLength = 0;
        int currMaxSeqLength = 0;
        for (int j : mask) {
            if (j == 0) {
                maxSeqLength = Math.max(currMaxSeqLength, maxSeqLength);
                currMaxSeqLength = 0;
            } else {
                currMaxSeqLength++;
            }
        }

        maxSeqLength = Math.max(currMaxSeqLength, maxSeqLength);

        return maxSeqLength;
    }
}
