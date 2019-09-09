package puzzler.leetcode.trie;

import org.testng.annotations.Test;

import static org.gradle.internal.impldep.org.junit.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuptsov
 */
public class AddAndSearchWord {

    class WordDictionary {

        class TrieNode {
            boolean aWord = false;
            TrieNode[] alphabet = new TrieNode['z' - 'a' + 1];
        }

        TrieNode root = new TrieNode();

        /**
         * Adds a word into the data structure.
         */
        public void addWord(String word) {
            if (word == null || word.length() == 0) {
                return;
            }

            TrieNode currentLvl = root;
            for (int i = 0; i < word.length(); i++) {
                int symbol = word.charAt(i) - 'a';
                if (currentLvl.alphabet[symbol] == null) {
                    currentLvl.alphabet[symbol] = new TrieNode();
                }

                currentLvl = currentLvl.alphabet[symbol];
            }

            currentLvl.aWord = true;
        }

        /**
         * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter.
         */
        public boolean search(String word) {
            if (word == null || word.length() == 0) {
                return false;
            }

            return search_(word, 0, root);
        }

        private boolean search_(String word, int fromIndex, TrieNode currentLvl) {
            for (int i = fromIndex; i < word.length(); i++) {
                if (word.charAt(i) == '.') {
                    for (TrieNode nextLvl : currentLvl.alphabet) {
                        if (nextLvl != null && search_(word, i + 1, nextLvl)) return true;
                    }

                    return false;
                }
                int symbol = word.charAt(i) - 'a';
                if (currentLvl.alphabet[symbol] == null) {
                    return false;
                } else {
                    currentLvl = currentLvl.alphabet[symbol];
                }
            }

            return currentLvl.aWord;
        }
    }

    @Test
    public void test() {
        WordDictionary wordDictionary = new WordDictionary();

        assertFalse(wordDictionary.search("a"));
        assertFalse(wordDictionary.search(""));

        wordDictionary.addWord("bad");
        wordDictionary.addWord("az");
        wordDictionary.addWord("dad");
        wordDictionary.addWord("mad");

        assertFalse(wordDictionary.search("pad"));
        assertTrue(wordDictionary.search("bad"));
        assertTrue(wordDictionary.search("az"));
        assertFalse(wordDictionary.search("za"));
        assertTrue(wordDictionary.search(".ad"));
        assertTrue(wordDictionary.search("..."));
        assertFalse(wordDictionary.search("."));
        assertFalse(wordDictionary.search("...."));
        assertTrue(wordDictionary.search("b.."));
        assertTrue(wordDictionary.search("b.d"));
    }
}
