package puzzler.leetcode.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given two binary strings, return their addBinary (also a binary string).
 * <p>
 * For example, a = "11", b = "1", the return is "100".
 */
public class AddBinary {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"11", "1", "100"},
                {"0", "0", "0"},
                {"101111", "10", "110001"},
                {"1", "111", "1000"}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String one, String two, String sum) {
        assertEquals(addBinary(one, two), sum);
    }

    public String addBinary(String first, String second) {
        int length1 = first.length();
        int length2 = second.length();
        if (length1 > second.length()) {
            for (int i = 1; i <= length1 - length2; i++) {
                second = "0" + second;
            }
        } else if (length1 < second.length()) {
            for (int i = 1; i <= length2 - length1; i++) {
                first = "0" + first;
            }
        }


        String s = "";
        boolean overflow = false;
        for (int i = second.length() - 1; i >= 0; i--) {
            char firstCh = first.charAt(i);
            char secondCh = second.charAt(i);

            if (firstCh == '1' && secondCh == '1') {
                if (overflow) {
                    s = "1" + s;
                    overflow = true;
                } else {
                    s = "0" + s;
                    overflow = true;
                }
            } else if (firstCh == '0' && secondCh == '0') {
                if (overflow) {
                    s = "1" + s;
                    overflow = false;
                } else {
                    s = "0" + s;
                }
            } else {
                if (overflow) {
                    s = "0" + s;
                    overflow = true;
                } else {
                    s = "1" + s;
                }
            }
        }
        if (overflow)

        {
            s = "1" + s;
        }

        return s;
    }
}
