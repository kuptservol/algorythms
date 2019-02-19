package puzzler.leetcode.all;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 */
public class Maze {


    int[][] maze1 = {
            {0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0},
            {1, 1, 0, 1, 1},
            {0, 0, 0, 0, 0},
    };

    int[][] maze2 = {
            {0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0},
            {1, 1, 0, 1, 1},
            {0, 0, 0, 0, 0},
    };

    int[][] maze3 = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
    };

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {maze1, intA(0, 4), intA(4, 4), true},
                {maze2, intA(0, 4), intA(3, 2), false},
                {maze3, intA(0, 0), intA(1, 2), false}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[][] maze, int[] start, int[] destination, boolean correctRes) {
        assertEquals(hasPath(maze, start, destination), correctRes);
    }

    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        if (maze == null || maze.length == 0) {
            return false;
        }

        return movingDFS(maze, start, destination);
    }

    private boolean movingDFS(int[][] maze, int[] start, int[] destination) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];

        return dfs(maze, start[0], start[1], destination, visited);
    }

    private boolean dfs(int[][] maze, int x, int y, int[] destination, boolean[][] visited) {
        if (visited[x][y]) {
            return false;
        } else {
            visited[x][y] = true;
        }

        if (x == destination[0] && y == destination[1]) {
            return true;
        }

        int l = x - 1;
        int r = x + 1;
        int u = y + 1;
        int d = y - 1;

        while (l >= 0 && maze[l][y] == 0) {
            l--;
        }
        if (dfs(maze, l + 1, y, destination, visited)) {
            return true;
        }

        while (r < maze.length && maze[r][y] == 0) {
            r++;
        }
        if (dfs(maze, r - 1, y, destination, visited)) {
            return true;
        }

        while (d >= 0 && maze[x][d] == 0) {
            d--;
        }
        if (dfs(maze, x, d + 1, destination, visited)) {
            return true;
        }

        while (u < maze[0].length && maze[x][u] == 0) {
            u++;
        }
        if (dfs(maze, x, u - 1, destination, visited)) {
            return true;
        }

        return false;
    }
}
