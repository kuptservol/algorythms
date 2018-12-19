package puzzler.ya.ctf2018;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author Sergey Kuptsov
 */
public class StrangeDigits {

    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        HttpClient client = new DefaultHttpClient();
        String uri = "https://weird-math.yactf.ru";

        HttpGet request = new HttpGet(uri);

        int tasksLeftCount;

        HttpResponse response = client.execute(request);
        Header tasksLeft = response.getFirstHeader("X-Weird-Math-Tasks-Left");

        tasksLeftCount = Integer.valueOf(tasksLeft.getValue());
        System.out.println("TASKS LEFT: " + tasksLeftCount);

        Header xWeirdMathToken = response.getFirstHeader("X-Weird-Math-Token");

        JsonNode jsonNode = mapper.readTree(response.getEntity().getContent());

        String expression = jsonNode.fields().next().getValue().asText();
        System.out.println("EXPRESSION: " + expression);

        Long answer = executeExpression(expression);

        while (tasksLeftCount > 0) {

            HttpPost post = new HttpPost(uri);
            post.setHeader(xWeirdMathToken);
            post.setEntity(new StringEntity(answer.toString()));

            HttpResponse answerResponse = client.execute(post);
            JsonNode jsonResponse = mapper.readTree(answerResponse.getEntity().getContent());
            System.out.println("RESPONSE: " + jsonResponse);

            if (answerResponse.getStatusLine().getStatusCode() != 200) {
                System.out.println("WRONG ANSWER, STOPPING");
                break;
            }
            xWeirdMathToken = answerResponse.getFirstHeader("X-Weird-Math-Token");

            expression = jsonResponse.fields().next().getValue().asText();

            tasksLeft = response.getFirstHeader("X-Weird-Math-Tasks-Left");
            tasksLeftCount = Integer.valueOf(tasksLeft.getValue());
            System.out.println("TASKS LEFT: " + tasksLeftCount);

            answer = executeExpression(expression);

            System.out.println("EXPRESSION: " + expression + " ANSWER: " + answer);
        }
    }

    private static Long executeExpression(String expression) {
        String[] operators = {"\\*", "/", "\\+", "\\-"};

        for (String operator : operators) {
            String[] split = expression.split(operator);

            if (split.length == 2) {

                Long left = numberFromString(split[0].trim());
                Long right = numberFromString(split[1].trim());

                System.out.println("LEFT: " + split[0].trim() + " PARSED TO: " + left);
                System.out.println("RIGHT: " + split[1].trim() + " PARSED TO: " + right);

                return res(left, right, operator);
            }
        }

        throw new RuntimeException("FOUND NOT KNOWN OPERATOR");
    }

    private static Long res(Long left, Long right, String operator) {
        switch (operator) {
            case "\\*":
                return left * right;
            case "\\-":
                return left - right;
            case "\\+":
                return left + right;
            case "/":
                return left / right;
            default:
                throw new RuntimeException("FOUND NOT KNOWN OPERATOR");
        }
    }

    private static Long numberFromString(String expression) {
        if (expression.matches("[0-9]+")) {
            return Long.valueOf(expression);
        } else if (expression.matches("[MDCLXVI]+")) {
            return romanToDecimal(expression);
        } else if (expression.matches("[a-zA-Z ]+")) {
            return englishWordsToNumber(expression.toLowerCase());
        } else if (expression.matches("[а-зА-З ]+")) {
            return russianWordsToNumber(expression.toLowerCase());
        } else if (expression.startsWith("0b")) {
            return Long.parseLong(expression.substring(2), 2);
        } else if (expression.startsWith("0x")) {
            return Long.parseLong(expression.substring(2), 16);
        } else if (expression.startsWith("0o")) {
            return Long.parseLong(expression.substring(2), 8);
        } else {
            return russianWordsToNumber(expression.toLowerCase());
        }

//        throw new RuntimeException("Unknown expression format: " + expression);
    }

    private static Long russianWordsToNumber(String expression) {

        long result = 0;
        long finalResult = 0;
        if (expression != null && expression.length() > 0) {
            String[] splittedParts = expression.trim().split("\\s+");

            for (String str : splittedParts) {
                if (str.equalsIgnoreCase("ноль")) {
                    result += 0;
                } else if (str.equalsIgnoreCase("один")) {
                    result += 1;
                } else if (str.equalsIgnoreCase("два")) {
                    result += 2;
                } else if (str.equalsIgnoreCase("три")) {
                    result += 3;
                } else if (str.equalsIgnoreCase("четыре")) {
                    result += 4;
                } else if (str.equalsIgnoreCase("пять")) {
                    result += 5;
                } else if (str.equalsIgnoreCase("шесть")) {
                    result += 6;
                } else if (str.equalsIgnoreCase("семь")) {
                    result += 7;
                } else if (str.equalsIgnoreCase("восемь")) {
                    result += 8;
                } else if (str.equalsIgnoreCase("девять")) {
                    result += 9;
                } else if (str.equalsIgnoreCase("десять")) {
                    result += 10;
                } else if (str.equalsIgnoreCase("одинадцать")) {
                    result += 11;
                } else if (str.equalsIgnoreCase("двеннадцать")) {
                    result += 12;
                } else if (str.equalsIgnoreCase("тринадцать")) {
                    result += 13;
                } else if (str.equalsIgnoreCase("четырнадцать")) {
                    result += 14;
                } else if (str.equalsIgnoreCase("пятнадцать")) {
                    result += 15;
                } else if (str.equalsIgnoreCase("шестнадцать")) {
                    result += 16;
                } else if (str.equalsIgnoreCase("семнадцать")) {
                    result += 17;
                } else if (str.equalsIgnoreCase("восемнадцать")) {
                    result += 18;
                } else if (str.equalsIgnoreCase("девятнадцать")) {
                    result += 19;
                } else if (str.equalsIgnoreCase("двадцать")) {
                    result += 20;
                } else if (str.equalsIgnoreCase("тридцать")) {
                    result += 30;
                } else if (str.equalsIgnoreCase("сорок")) {
                    result += 40;
                } else if (str.equalsIgnoreCase("пятьдесят")) {
                    result += 50;
                } else if (str.equalsIgnoreCase("шестьдесят")) {
                    result += 60;
                } else if (str.equalsIgnoreCase("семьдесят")) {
                    result += 70;
                } else if (str.equalsIgnoreCase("восемьдесят")) {
                    result += 80;
                } else if (str.equalsIgnoreCase("девяносто")) {
                    result += 90;
                } else if (str.equalsIgnoreCase("сто")) {
                    result += 100;
                } else if (str.equalsIgnoreCase("двести")) {
                    result += 200;
                } else if (str.equalsIgnoreCase("триста")) {
                    result += 300;
                } else if (str.equalsIgnoreCase("четыреста")) {
                    result += 400;
                } else if (str.equalsIgnoreCase("пятьсот")) {
                    result += 500;
                } else if (str.equalsIgnoreCase("шестьсот")) {
                    result += 600;
                } else if (str.equalsIgnoreCase("семьсот")) {
                    result += 700;
                } else if (str.equalsIgnoreCase("восемьсот")) {
                    result += 800;
                } else if (str.equalsIgnoreCase("девятьсот")) {
                    result += 900;
                }
            }

            finalResult += result;
        }

        return finalResult;
    }

    private static Long englishWordsToNumber(String expression) {

        boolean isValidInput = true;
        long result = 0;
        long finalResult = 0;
        List<String> allowedStrings = Arrays.asList
                (
                        "zero", "one", "two", "three", "four", "five", "six", "seven",
                        "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen",
                        "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty",
                        "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety",
                        "hundred", "thousand", "million", "billion", "trillion"
                );

        if (expression != null && expression.length() > 0) {
            expression = expression.replaceAll("-", " ");
            expression = expression.toLowerCase().replaceAll(" and", " ");
            String[] splittedParts = expression.trim().split("\\s+");

            for (String str : splittedParts) {
                if (!allowedStrings.contains(str)) {
                    isValidInput = false;
                    System.out.println("Invalid word found : " + str);
                    break;
                }
            }
            if (isValidInput) {
                for (String str : splittedParts) {
                    if (str.equalsIgnoreCase("zero")) {
                        result += 0;
                    } else if (str.equalsIgnoreCase("one")) {
                        result += 1;
                    } else if (str.equalsIgnoreCase("two")) {
                        result += 2;
                    } else if (str.equalsIgnoreCase("three")) {
                        result += 3;
                    } else if (str.equalsIgnoreCase("four")) {
                        result += 4;
                    } else if (str.equalsIgnoreCase("five")) {
                        result += 5;
                    } else if (str.equalsIgnoreCase("six")) {
                        result += 6;
                    } else if (str.equalsIgnoreCase("seven")) {
                        result += 7;
                    } else if (str.equalsIgnoreCase("eight")) {
                        result += 8;
                    } else if (str.equalsIgnoreCase("nine")) {
                        result += 9;
                    } else if (str.equalsIgnoreCase("ten")) {
                        result += 10;
                    } else if (str.equalsIgnoreCase("eleven")) {
                        result += 11;
                    } else if (str.equalsIgnoreCase("twelve")) {
                        result += 12;
                    } else if (str.equalsIgnoreCase("thirteen")) {
                        result += 13;
                    } else if (str.equalsIgnoreCase("fourteen")) {
                        result += 14;
                    } else if (str.equalsIgnoreCase("fifteen")) {
                        result += 15;
                    } else if (str.equalsIgnoreCase("sixteen")) {
                        result += 16;
                    } else if (str.equalsIgnoreCase("seventeen")) {
                        result += 17;
                    } else if (str.equalsIgnoreCase("eighteen")) {
                        result += 18;
                    } else if (str.equalsIgnoreCase("nineteen")) {
                        result += 19;
                    } else if (str.equalsIgnoreCase("twenty")) {
                        result += 20;
                    } else if (str.equalsIgnoreCase("thirty")) {
                        result += 30;
                    } else if (str.equalsIgnoreCase("forty")) {
                        result += 40;
                    } else if (str.equalsIgnoreCase("fifty")) {
                        result += 50;
                    } else if (str.equalsIgnoreCase("sixty")) {
                        result += 60;
                    } else if (str.equalsIgnoreCase("seventy")) {
                        result += 70;
                    } else if (str.equalsIgnoreCase("eighty")) {
                        result += 80;
                    } else if (str.equalsIgnoreCase("ninety")) {
                        result += 90;
                    } else if (str.equalsIgnoreCase("hundred")) {
                        result *= 100;
                    } else if (str.equalsIgnoreCase("thousand")) {
                        result *= 1000;
                        finalResult += result;
                        result = 0;
                    } else if (str.equalsIgnoreCase("million")) {
                        result *= 1000000;
                        finalResult += result;
                        result = 0;
                    } else if (str.equalsIgnoreCase("billion")) {
                        result *= 1000000000;
                        finalResult += result;
                        result = 0;
                    } else if (str.equalsIgnoreCase("trillion")) {
                        result *= 1000000000000L;
                        finalResult += result;
                        result = 0;
                    }
                }

                finalResult += result;
            }

        }

        return finalResult;
    }

    public static long romanToDecimal(java.lang.String romanNumber) {
        long decimal = 0;
        long lastNumber = 0;
        String romanNumeral = romanNumber.toUpperCase();

        for (int x = romanNumeral.length() - 1; x >= 0; x--) {
            char convertToDecimal = romanNumeral.charAt(x);

            switch (convertToDecimal) {
                case 'M':
                    decimal = processDecimal(1000, lastNumber, decimal);
                    lastNumber = 1000;
                    break;

                case 'D':
                    decimal = processDecimal(500, lastNumber, decimal);
                    lastNumber = 500;
                    break;

                case 'C':
                    decimal = processDecimal(100, lastNumber, decimal);
                    lastNumber = 100;
                    break;

                case 'L':
                    decimal = processDecimal(50, lastNumber, decimal);
                    lastNumber = 50;
                    break;

                case 'X':
                    decimal = processDecimal(10, lastNumber, decimal);
                    lastNumber = 10;
                    break;

                case 'V':
                    decimal = processDecimal(5, lastNumber, decimal);
                    lastNumber = 5;
                    break;

                case 'I':
                    decimal = processDecimal(1, lastNumber, decimal);
                    lastNumber = 1;
                    break;
            }
        }

        return decimal;
    }

    public static long processDecimal(long decimal, long lastNumber, long lastDecimal) {
        if (lastNumber > decimal) {
            return lastDecimal - decimal;
        } else {
            return lastDecimal + decimal;
        }
    }
}
