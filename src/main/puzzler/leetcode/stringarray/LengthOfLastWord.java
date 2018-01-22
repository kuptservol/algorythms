package puzzler.leetcode.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 */
public class LengthOfLastWord {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"Hello World", 5},
                {"World", 5},
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s, int n) {
        assertEquals(lengthOfLastWord(s), n);
    }

    public int lengthOfLastWord(String s) {
        String[] split = s.split(" ");
        return split.length > 0 ? split[split.length - 1].length() : 0;
    }
}
