package ru.skuptsov.puzzlers.adventofcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergey Kuptsov
 * @since 19/06/2016
 */

//a-z 97 - 122
public class SantaPassword {

    public static boolean checkPassword(char[] password) {

        Map<Character, Boolean> pairsMap = new HashMap<>();
        boolean foundSequence = false;

//        debug
//        String s = new String(password);
//        System.out.println(s);
//        if (s.equals("ghjaabcc")) {
//            System.out.println("found");
//        }

        for (int i = 0; i < password.length; i++) {

            char p = password[i];

            if (i < password.length - 1 - 2) {
                if (p + 1 == password[i + 1] && p + 2 == password[i + 2]) {
                    foundSequence = true;
                }
            }

            if (p == 'i' || p == 'o' || p == 'l') {
                return false;
            }

            if (i <= password.length - 1 - 1) {
                if (p == password[i + 1]) {
                    if (!pairsMap.containsKey(p)) {
                        pairsMap.put(p, true);
                    }
                }
            }
        }

        int pairsCount = 0;
        for (Map.Entry<Character, Boolean> entry : pairsMap.entrySet()) {
            if (entry.getValue()) {
                pairsCount++;
            }

        }

        if (pairsCount < 2) {
            return false;
        }

        if (!foundSequence) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {

        String password = "vzbxxyzz";
        WordCyclicBuffer wordCyclicBuffer = new WordCyclicBuffer(password);
        while (wordCyclicBuffer.hasNext) {
            char[] next = wordCyclicBuffer.next();
            if (checkPassword(next)) {
                String s = new String(next);
                System.out.println(s);
                break;
            }
        }
    }

    private static class WordCyclicBuffer {
        private final int N;
        private final char[] words;
        private final char start = 97;
        private final char end = 122;
        private int pointer;
        private boolean hasNext = true;

        private WordCyclicBuffer(String s) {
            char[] current = s.toCharArray();
            N = current.length;
            words = new char[N];

            System.arraycopy(current, 0, words, 0, N);

            pointer = N - 1;
        }

        public char[] next() {
            incRightMost();
            return words;
        }

        public boolean hasNext() {
            return hasNext;
        }

        private void incRightMost() {
            if (words[pointer] == end) {
                words[pointer] = start;
                if (pointer > 0) {
                    pointer--;
                    incRightMost();
                } else {
                    hasNext = false;
                }
            } else {
                words[pointer]++;
                pointer = N - 1;
            }
        }
    }
}
