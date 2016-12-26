package ru.skuptsov.puzzlers.job.interview.leetcode.stringarray;

/**
 * @author Sergey Kuptsov
 * @since 07/10/2016
 * Implement wildcard pattern matching with support for '?' and '*'.
 */
public class WildCardMatching {

    private final static String or = "abbcccc";
    private final static String w = "a*b*cc";
    private final static String q = "aa?abbb??ccc";

    public static void main(String[] args) {
        System.out.println(matches(or, w));
        System.out.println(matches(or, "aaaabbbbccccd"));
        System.out.println(matches(or, q));
        System.out.println(matches("abdbabc", "a*ba*c"));
    }

    private static boolean matches(String or, String reg) {
        int i = 0;
        int j = 0;
        int lastStarIndex = -1;
        int iIndex = -1;

        while (i < or.length()) {
            if (j < reg.length() && (reg.charAt(j) == '?' || reg.charAt(j) == or.charAt(i))) {
                i++;
                j++;
            } else if (j < reg.length() && reg.charAt(j) == '*') {
                lastStarIndex = j;
                j++;
                iIndex = i;
            } else if (lastStarIndex != -1) {
                j = lastStarIndex + 1;
                i = iIndex + 1;
                iIndex++;
            } else {
                return false;
            }
        }

        return j == reg.length();
    }
}
