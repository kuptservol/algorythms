package ru.skuptsov.puzzlers.job.interview.leetcode.stringarray;

import org.springframework.util.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.lang.String.valueOf;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 15/10/2016
 * Implement atoi to convert a string to an integer.
 */
public class StringToInteger {

    public static void main(String[] args) {
        char t = 48;
        System.out.println(t);
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"67", 67},
                {"734", 734},
                {"67a", null},
                {null, null},
                {"", null},
                {" ", null},
                {"-", null},
                {"-700", -700},
                {"+701", 701},
                {valueOf(Long.MAX_VALUE), null}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String a, Integer b) {
        assertEquals(stringToInt(a), b);
    }

    private Integer stringToInt(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        int v = 0;
        int r = 1;
        int s = 1;
        int c = 0;

        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char next = chars[i];
            if (i == 0 && (next == '-' || next == '+')) {
                if (chars.length == 1) {
                    return null;
                }
                if (next == '-') {
                    s = -1;
                }
            } else {
                int num = next - 48;
                if (c > 8) {
                    return null;
                }
                if (num < 0 || num > 9) {
                    return null;
                } else {
                    v = r * v + num;
                    c++;
                    r = 10;
                }
            }
        }

        return v * s;
    }
}
