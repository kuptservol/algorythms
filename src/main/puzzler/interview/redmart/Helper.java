package puzzler.interview.redmart;

/**
 * @author Sergey Kuptsov
 * @since 14/05/2016
 */
public class Helper {


    /**
     * Check if token is cell link
     *
     * @param token
     * @return
     */
    public static boolean isCellLink(String token) {

        try {

            char ch0 = token.charAt(0);
            int temp = (int) ch0;
            if (!(temp <= 90 & temp >= 65))
                return false;
            if (!Character.isAlphabetic(ch0))
                return false;
            String ch1_n = token.substring(1, token.length());
            if (!isNumeric(ch1_n))
                return false;

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Check if string value is numeric
     *
     * @param str number string value
     * @return true if numeric
     */
    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
