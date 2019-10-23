package puzzler.interview;

import java.util.LinkedList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 */
public class DeleteUnbalancedSmiles {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"Я работаю в Яндексе)))", "Я работаю в Яндексе"},
                {"везет :) а я туда тоже попаду (:(:", "везет : а я туда тоже попаду ::"},
                {"лол (с)", "лол (с)"},
                {"лол (((с)))", "лол (((с)))"},
                {"лол )((с))(", "лол ((с))"}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s, String res) {
        assertEquals(deleteUnbalancedSmiles(s), res);
    }

    public String deleteUnbalancedSmiles(String input) {
        if (input == null || input.length() < 3) {
            return input;
        }

        LinkedList<BracePos> braces = new LinkedList<>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                braces.addLast(new BracePos('(', i));
            } else if (input.charAt(i) == ')') {
                if (braces.size() > 0 && braces.peekLast().type == '(') {
                    braces.pollLast();
                } else {
                    braces.addLast(new BracePos(')', i));
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            BracePos bracePos = braces.peekFirst();
            if (bracePos != null && bracePos.pos == i) {
                braces.pollFirst();
            } else {
                stringBuilder.append(input.charAt(i));
            }
        }

        return stringBuilder.toString();
    }

    private final static class BracePos {
        private final char type;
        private final int pos;

        BracePos(char type, int pos) {
            this.type = type;
            this.pos = pos;
        }
    }
}
