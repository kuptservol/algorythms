package ru.skuptsov.algorythms.string.substringsearch;

import java.util.Arrays;

/**
 * @author Sergey Kuptsov
 * @since 22/10/2016
 */
public class BoyerMoore {
    private final static int alphabetLength = (int) 'z' - (int) 'A';
    private final static int[] indexedAlphabet = new int[alphabetLength];

    static {
        for (int i = 0; i < alphabetLength; i++) {
            indexedAlphabet[i] = -1;
        }
    }

    public static int substringSearch(String haystack, String needle) {
        int res = -1;
        if (haystack.length() < needle.length()) {
            return res;
        }
        int i = 0;
        int[] leftIndexedNeedle = indexNeedle(needle);
        char[] h = haystack.toCharArray();
        char[] n = needle.toCharArray();

        int nLastI = n.length - 1;
        while (i < h.length) {
            char nextH = h[i + nLastI];
            if (nextH == n[nLastI]) {
                for (int j = nLastI; j >= 0; j--) {
                    if (h[j + i] != n[j]) {
                        i += nLastI - leftIndexedNeedle[nextH - 'A'];
                        break;
                    }
                    if (j == 0) {
                        return i;
                    }
                }
            } else {
                if (leftIndexedNeedle[nextH - 'A'] != -1) {
                    i += nLastI - leftIndexedNeedle[nextH - 'A'];
                } else {
                    i += n.length;
                }
            }
        }

        return res;
    }

    private static int[] indexNeedle(String needle) {
        int[] indexedNeedle = Arrays.copyOf(indexedAlphabet, indexedAlphabet.length);
        char[] chars = needle.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            int next = chars[i];
            indexedNeedle[next - 'A'] = i;
        }

        return indexedNeedle;
    }
}
