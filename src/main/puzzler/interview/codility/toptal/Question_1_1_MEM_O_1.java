package puzzler.interview.codility.toptal;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 */
public class Question_1_1_MEM_O_1 {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"(())", 2},
                {"(())))(", 4},
                {"))", 2},
                {"((()))", 3},
                {"((()()))", 4}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s, int res) {
        assertEquals(solution(s), res);
    }

    public int solution(String S) {

        char[] ch = S.toCharArray();
        int openBraceCount = 0;
        int closedBraceCount = 0;
        int i = 0;
        int j = S.length() - 1;

        while (i <= j) {
            while (ch[++i] != ')') if (i >= ch.length - 1 || i >= j) break;
            openBraceCount++;

            while (ch[--j] != '(') if (j < 0 || i >= j) break;
            closedBraceCount++;
        }

        return 0;
    }
}
