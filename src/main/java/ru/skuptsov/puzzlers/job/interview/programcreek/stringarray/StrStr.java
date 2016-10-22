package ru.skuptsov.puzzlers.job.interview.programcreek.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.skuptsov.algorythms.string.substringsearch.BoyerMoore;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 22/10/2016
 * Implement strStr().
 * <p>
 * Returns the index of the first occurrence of needle in haystack,
 * or -1 if needle is not part of haystack.
 */
public class StrStr {
    /**
     * 012345678901234567890123
     * FINDINAHAYSTACKNEEDLEINA
     * NEEDLE
     */
    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"FINDINAHAYSTACKNEEDLEINA", "NEEDLE", 15},
                {"FINDINAHAYSTACKNEEDLEINA", "NEDLE", -1}
        };
    }

    private int strStr(String haystack, String needle) {
        return BoyerMoore.substringSearch(haystack, needle);
    }

    @Test(dataProvider = "testData")
    public void test(String a, String b, int c) {
        assertEquals(strStr(a, b), c);
    }
}
