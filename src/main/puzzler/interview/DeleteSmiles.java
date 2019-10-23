package puzzler.interview;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * delete smiles with regular expression ":-\)+|:-\(+"
 */
public class DeleteSmiles {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {":-)):-)(", "("},
                {"Я работаю в Гугле :-)))", "Я работаю в Гугле "},
                {"везет :-) а я туда собеседование завалил:-((", "везет  а я туда собеседование завалил"},
                {"лол:)", "лол:)"},
                {"Ааааа!!!!! :-))(())", "Ааааа!!!!! (())"},
                {":-)(", "("},
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s, String res) {
        assertEquals(deleteSmiles(s), res);
    }

    public String deleteSmiles(String input) {
        if (input == null || input.length() < 3) {
            return input;
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            if (i + 2 < input.length() && input.charAt(i) == ':' && input.charAt(i + 1) == '-' &&
                    (input.charAt(i + 2) == '(' || input.charAt(i + 2) == ')'))
            {
                int j = i + 3;
                for (; j < input.length(); j++) {
                    if (input.charAt(j) != input.charAt(j - 1)) {
                        break;
                    }
                }

                i = j - 1;
            } else{
                stringBuilder.append(input.charAt(i));
            }
        }

        return stringBuilder.toString();
    }
}
