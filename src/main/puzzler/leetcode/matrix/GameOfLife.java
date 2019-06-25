package puzzler.leetcode.matrix;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import puzzler.leetcode.PuzzlerUtils;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *
 * According to the Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by the British
 * mathematician John Horton Conway in 1970."
 *
 * Given a board with m by n cells, each cell has an initial state live (1) or dead (0).
 * Each cell interacts with its eight neighbors
 * (horizontal, vertical, diagonal) using the following four rules
 * (taken from the above Wikipedia article):
 *
 * Any live cell with fewer than two live neighbors dies, as if caused by under-population.
 * Any live cell with two or three live neighbors lives on to the next generation.
 * Any live cell with more than three live neighbors dies, as if by over-population..
 * Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 * Write a function to compute the next state (after one update) of the board given its current state.
 * The next state is created by
 * applying the above rules simultaneously to every cell in the current state,
 * where births and deaths occur simultaneously.
 */
public class GameOfLife {

    int[][] board1 = {
            {0, 1, 0},
            {0, 0, 1},
            {1, 1, 1},
            {0, 0, 0}
    };

    int[][] boardLifed1 = {
            {0, 0, 0},
            {1, 0, 1},
            {0, 1, 1},
            {0, 1, 0}
    };

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {board1, boardLifed1}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[][] board, int[][] boardLifed) {
        gameOfLife(board);
        PuzzlerUtils.printMatrix(board);
        assertEquals(board, boardLifed);
    }

    /**
     * rather boring:
     *
     * simply applying rules of game to another board cell by cell - then copy it values back
     * mem: O(N) speed: O(N)
     *
     * Passed
     * But there is really in-place solution - where we can set prev info about cell value with some
     * other value - prev 1 but now 0 - so -1 f.e.
     */
    public void gameOfLife(int[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }

        int[][] lifeBoard = new int[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int liveNeighbours = countLiveNeighbours(board, i, j);
                if (board[i][j] == 0) {
                    if (liveNeighbours == 3) {
                        lifeBoard[i][j] = 1;
                    }
                } else {
                    if (liveNeighbours < 2 || liveNeighbours > 3) {
                        lifeBoard[i][j] = 0;
                    } else {
                        lifeBoard[i][j] = 1;
                    }
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            System.arraycopy(lifeBoard[i], 0, board[i], 0, board[0].length);
        }
    }


    private int countLiveNeighbours(int[][] board, int i, int j) {
        return isLiveNeighbours(board, i - 1, j - 1)
                + isLiveNeighbours(board, i - 1, j)
                + isLiveNeighbours(board, i + 1, j)
                + isLiveNeighbours(board, i, j - 1)
                + isLiveNeighbours(board, i, j + 1)
                + isLiveNeighbours(board, i + 1, j + 1)
                + isLiveNeighbours(board, i - 1, j + 1)
                + isLiveNeighbours(board, i + 1, j - 1);
    }

    private int isLiveNeighbours(int[][] board, int i, int j) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
            return 0;
        }

        return board[i][j];
    }
}
