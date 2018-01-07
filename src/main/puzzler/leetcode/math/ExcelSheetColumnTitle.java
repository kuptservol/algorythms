package puzzler.leetcode.math;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a positive integer, return its corresponding column title as appear in an Excel sheet.
 */
public class ExcelSheetColumnTitle {

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
        assertEquals(convertToTitle(result), s);
    }

    public String convertToTitle(int n) {
        String result = "";

        while (n > 0) {
            n--;
            result = Character.toString((char) (n % 26 + 'A')) + result;

            n = n / 26;
        }

        return result;
    }
}
