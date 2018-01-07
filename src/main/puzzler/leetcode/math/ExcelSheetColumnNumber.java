package puzzler.leetcode.math;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a column title as appear in an Excel sheet, return its corresponding column number.
 */
public class ExcelSheetColumnNumber {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {1, "A"},
                {2, "B"},
                {3, "C"},
                {0, ""},
                {26, "Z"},
                {27, "AA"},
                {28, "AB"},
                {703, "AAA"},
                {18279, "AAAA"},
                {458330, "ZAZB"},
                {494265, "ABCDE"}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int result, String s) {
        assertEquals(titleToNumber(s), result);
    }

    public int titleToNumber(String s) {
        int result = 0;
        int razr = 1;

        char[] chars = s.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            result += razr * (chars[i] - 'A' + 1);
            razr *= 26;
        }

        return result;
    }
}
