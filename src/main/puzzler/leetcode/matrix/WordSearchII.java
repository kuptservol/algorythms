package puzzler.leetcode.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.strA;

/**
 * @author Sergey Kuptsov
 *         Given a 2D board and a list of words from the dictionary, find all words in the board.
 *         <p>
 *         Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring.
 *         The same letter cell may not be used more than once in a word.
 */
public class WordSearchII {

    char[][] board1 = {
            {'o', 'a', 'a', 'n'},
            {'e', 't', 'a', 'e'},
            {'i', 'h', 'k', 'r'},
            {'i', 'f', 'l', 'v'},
    };

    char[][] board2 = {
            {'a', 'b'},
            {'a', 'a'},
    };

    char[][] board3 = {
            {'a', 'b', 'c'},
            {'a', 'e', 'd'},
            {'a', 'f', 'g'},
    };

    char[][] board4 = {
            {'a', 'a'}
    };

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {board1, strA("oath", "pea", "eat", "rain"), of("eat", "oath")},
                {board2, strA("aba", "baa", "bab", "aaab", "aaa", "aaaa", "aaba"), of("aaa", "aaab", "aaba", "aba", "baa")},
                {board3, strA("abcdefg", "gfedcbaaa", "eaabcdgfa", "befa", "dgc", "ade"), of("abcdefg", "befa", "eaabcdgfa", "gfedcbaaa")},
                {board4, strA("a"), of("a")},
        };
    }

    @Test(dataProvider = "testData")
    public void test(char[][] board, String[] dict, List<String> words) {
        assertEquals(new HashSet<>(findWords(board, dict)), new HashSet<>(words));
    }

    public List<String> findWords(char[][] board, String[] words) {
        if (words.length == 0 || board.length == 0 || board[0].length == 0) {
            return new ArrayList<>();
        }

        //return dfs(board, words);
        return dfsOnTrie(board, words);
    }

    /**
     * One pass, stop when no candidates
     */
    public List<String> dfsOnTrie(char[][] board, String[] dict) {
        Set<String> words = new HashSet<>();

        Trie trie = new Trie();
        for (String s : dict) {
            trie.add(s);
        }

        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfsOnTrie(board, trie, "", visited, words, i, j);
            }
        }

        return new ArrayList<>(words);
    }

    private void dfsOnTrie(char[][] board, Trie trie, String currStr, boolean[][] visited, Set<String> words, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
            return;
        }

        if (visited[i][j]) {
            return;
        }

        currStr = currStr + board[i][j];

        if (!trie.startsWith(currStr)) {
            return;
        }

        if (trie.search(currStr)) {
            words.add(currStr);
        }

        visited[i][j] = true;
        dfsOnTrie(board, trie, currStr, visited, words, i + 1, j);
        dfsOnTrie(board, trie, currStr, visited, words, i - 1, j);
        dfsOnTrie(board, trie, currStr, visited, words, i, j - 1);
        dfsOnTrie(board, trie, currStr, visited, words, i, j + 1);
        visited[i][j] = false;
    }

    private final class Trie {
        TrieNode root = new TrieNode();

        void add(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TrieNode();
                }

                node = node.children[c - 'a'];
            }

            node.word = word;
        }

        boolean search(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TrieNode();
                }

                node = node.children[c - 'a'];
            }

            return node.word.equals(word);
        }

        boolean startsWith(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    return false;
                }

                node = node.children[c - 'a'];
            }

            return true;
        }

        private final class TrieNode {
            TrieNode[] children = new TrieNode[26];
            String word = "";
        }

    }

    /**
     * Dfs with some little optimizations:
     * - one pass through with list of candidates
     * - dict toCharArray cache
     * - visited optimization(not recreating)
     * <p>
     * Time Limit Exceeded
     */
    public List<String> dfs(char[][] board, String[] dict) {
        Set<String> words = new HashSet<>();

        Map<Integer, boolean[][]> visited = new HashMap<>();
        List<Integer> candidates = new ArrayList<>();

        List<char[]> dict_ = new ArrayList<>();
        for (int i = 0; i < dict.length; i++) {
            candidates.add(i);
            visited.put(i, new boolean[board.length][board[0].length]);
            dict_.add(i, dict[i].toCharArray());
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                dfs(words, dict_, board, i, j, 0, visited, candidates);
            }
        }

        return new ArrayList<>(words);
    }

    private void dfs(Set<String> words, List<char[]> dict, char[][] board, int i, int j, int currIndex,
            Map<Integer, boolean[][]> visited, List<Integer> candidates)
    {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || candidates.size() == 0) {
            return;
        }

        List<Integer> newCandidates = new ArrayList<>();
        for (Integer candidateI : candidates) {
            if (visited.get(candidateI)[i][j]) {
                continue;
            }

            if (dict.get(candidateI)[currIndex] == board[i][j]) {
                if (currIndex == dict.get(candidateI).length - 1) {
                    words.add(new String(dict.get(candidateI)));
                } else {
                    newCandidates.add(candidateI);
                }
            }
        }

        for (Integer candidateI : newCandidates) {
            visited.get(candidateI)[i][j] = true;
        }

        dfs(words, dict, board, i, j + 1, currIndex + 1, visited, newCandidates);
        dfs(words, dict, board, i, j - 1, currIndex + 1, visited, newCandidates);
        dfs(words, dict, board, i + 1, j, currIndex + 1, visited, newCandidates);
        dfs(words, dict, board, i - 1, j, currIndex + 1, visited, newCandidates);

        for (Integer candidateI : newCandidates) {
            visited.get(candidateI)[i][j] = false;
        }
    }
}
