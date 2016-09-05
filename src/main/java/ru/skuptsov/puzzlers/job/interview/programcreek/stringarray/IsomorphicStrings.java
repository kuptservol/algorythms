package ru.skuptsov.puzzlers.job.interview.programcreek.stringarray;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Kuptsov
 * @since 05/09/2016
 * <p>
 * Given two strings s and t, determine if they are isomorphic.
 * Two strings are isomorphic if the characters in s can be replaced to get t.
 * <p>
 * For example,"egg" and "add" are isomorphic, "foo" and "bar" are not.
 */
public class IsomorphicStrings {

    public static void main(String[] args) {
        System.out.println(isIsomorph("egg", "add") == true);
        System.out.println(isIsomorph("foo", "bar") != true);
    }

    private static boolean isIsomorph(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        Map<Character, Character> mapping = new HashMap<>();

        char[] chars_first = first.toCharArray();
        char[] chars_second = second.toCharArray();

        for (int i = 0; i < chars_first.length; i++) {

            char el_first = chars_first[i];

            if (mapping.containsKey(el_first)) {
                char target_el_second = mapping.get(el_first);
                if (target_el_second != chars_second[i]) {
                    return false;
                }
            } else {
                if (mapping.containsKey(chars_second[i])) {
                    return false;
                }
                mapping.put(chars_first[i], chars_second[i]);
            }
        }

        return true;
    }
}
