package puzzler.leetcode.matrix;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Given a 2D board and a word, find if the word exists in the grid.
 *         <p>
 *         The word can be constructed from letters of sequentially adjacent cell,
 *         where "adjacent" cells are those horizontally or vertically neighboring.
 *         The same letter cell may not be used more than once.
 *         <p>
 *         Example:
 *         <p>
 *         board =
 *         [
 *         ['A','B','C','E'],
 *         ['S','F','C','S'],
 *         ['A','D','E','E']
 *         ]
 *         <p>
 *         Given word = "ABCCED", return true.
 *         Given word = "SEE", return true.
 *         Given word = "ABCB", return false.
 */
public class WordSearch {

    char[][] board = {
            {'A', 'B', 'C', 'E'},
            {'S', 'F', 'C', 'S'},
            {'A', 'D', 'E', 'E'},
    };

    char[][] board4 = {
            {'A', 'B', 'C', 'E'},
            {'S', 'F', 'E', 'S'},
            {'A', 'D', 'E', 'E'},
    };

    char[][] board3 = {
            {'C', 'A', 'A'},
            {'A', 'A', 'A'},
            {'B', 'C', 'D'},
    };

    char[][] board2 = {
            {'A', 'A'}
    };

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {board, "ABCCED", true},
                {board, "SEE", true},
                {board, "ABCB", false},
                {board, "AA", false},
                {board, "A", true},
                {board2, "AAA", false},
                {board3, "AAB", true},
                {board4, "ABCESEEEFS", true},
        };
    }

    @Test(dataProvider = "testData")
    public void test(char[][] board, String word, boolean exists) {
        assertEquals(exist(board, word), exists);
    }

    public boolean exist(char[][] board, String word) {
        if (word.equals("") || board.length == 0) {
            return false;
        }

        char[] wordCh = word.toCharArray();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == wordCh[0]) {
                    if (wordCh.length == 1) {
                        return true;
                    }

                    if (exist(board, i, j, wordCh, 0, new HashSet<>())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean exist(char[][] board, int boardX, int boardY, char[] word, int nextWordI, Set<Long> visited) {
        long nextI = boardX << 30 | boardY;
        if (visited.contains(nextI)) {
            return false;
        }
        visited.add(nextI);

        if (nextWordI == word.length - 1) {
            return true;
        }

        char next = word[++nextWordI];
        int X = board.length - 1;
        int Y = board[0].length - 1;

        if (boardX != 0) {
            int nextBoardX = boardX - 1;
            char up = board[nextBoardX][boardY];
            if (up == next) {
                if (exist(board, nextBoardX, boardY, word, nextWordI, new HashSet<>(visited))) {
                    return true;
                }
            }
        }

        if (boardX != X) {
            int nextBoardX = boardX + 1;
            char down = board[nextBoardX][boardY];
            if (down == next) {
                if (exist(board, nextBoardX, boardY, word, nextWordI, new HashSet<>(visited))) {
                    return true;
                }
            }
        }

        if (boardY != 0) {
            int nextBoardY = boardY - 1;
            char left = board[boardX][nextBoardY];
            if (left == next) {
                if (exist(board, boardX, nextBoardY, word, nextWordI, new HashSet<>(visited))) {
                    return true;
                }
            }
        }

        if (boardY != Y) {
            int nextBoardY = boardY + 1;
            char right = board[boardX][nextBoardY];
            if (right == next) {
                if (exist(board, boardX, nextBoardY, word, nextWordI, new HashSet<>(visited))) {
                    return true;
                }
            }
        }

        return false;
    }
}
