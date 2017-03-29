package ru.skuptsov.puzzlers.job.interview.codility.toptal;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 */
public class Question_1_1 {

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

        int[] openBracketIndex = new int[S.length()];
        int[] closedBracketIndex = new int[S.length()];
        int openBracketIndexCount = 0;
        int closedBracketIndexCount = 0;

        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == '(') {
                openBracketIndexCount += 1;
            }

            openBracketIndex[i] = openBracketIndexCount;

            if (ch[ch.length - 1 - i] == ')') {
                closedBracketIndexCount += 1;
            }

            closedBracketIndex[ch.length - 1 - i] = closedBracketIndexCount;
        }

        for (int i = 0; i < ch.length; i++) {

            int closedBracketIndexVal = i == ch.length - 1 ? 0 : closedBracketIndex[i + 1];

            if (openBracketIndex[i] == closedBracketIndexVal) {
                return i + 1;
            }
        }

        return 0;
    }
}
