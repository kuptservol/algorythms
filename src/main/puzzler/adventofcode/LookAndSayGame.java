package puzzler.adventofcode;

/**
 * Created by Сергей on 13.05.2016.
 */
public class LookAndSayGame {

    static int N = 50;
    static String token = "3113322113";

    public static void main(String[] args) {
        LookAndSayGame lookAndSayGame = new LookAndSayGame();

        for (int i = 0; i < N; i++) {
            token = lookAndSayGame.lookAndSay(token);
        }

        System.out.println(token.length());

    }

    public String lookAndSay(String value) {
        char[] numbers = String.valueOf(value).toCharArray();
        StringBuilder countedNumber = new StringBuilder();

        for (int i = 0; i < numbers.length; i++) {
            int j = 1;
            while (i + j < numbers.length && numbers[i] == numbers[i + j]) {
                j++;
            }
            countedNumber.append(j);
            countedNumber.append(numbers[i]);
            i += j - 1;
        }

        return countedNumber.toString();
    }
}
