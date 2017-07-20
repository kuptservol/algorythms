package puzzler.interview.leetcode.matrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a 2-d grid map of '1's (land) and '0's (water), count the number of islands
 * . An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
 * You may assume all four edges of the grid are all surrounded by water.
 * <p>
 * Example 1:
 * <p>
 * 11110
 * 11010
 * 11000
 * 00000
 * \
 * Answer: 1
 */
public class NumberOfIslands {

    char[][] map1 = {
            {'1', '1', '1', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
    };

    char[][] map3 = {
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
    };

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {map1, 1},
                {map3, 3}
        };
    }

    @Test(dataProvider = "testData")
    public void test(char[][] map, int n) {
        assertEquals(numIslands(map), n);
    }

    public int numIslands(char[][] grid) {
        int islandsNum = 0;
        if (grid.length == 0) return islandsNum;

        int Y = grid[0].length;
        int X = grid.length;

        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                int p = grid[i][j];

                if (p == '0' || p == '2') {
                    continue;
                }

                walkThroughIsland(i, j, grid, X, Y);
                islandsNum++;
            }
        }

        return islandsNum;
    }

    private void walkThroughIsland(int i, int j, char[][] map, int X, int Y) {
        LinkedList<Point> walkQ = new LinkedList<>();

        Point start = new Point(i, j);
        walkQ.add(start);

        Point next;
        while ((next = walkQ.pollFirst()) != null) {
            walk(next, map);

            for (Point point : getLinked(next, map, X, Y)) {
                walkQ.add(point);
            }
        }
    }

    public List<Point> getLinked(Point p, char[][] map, int X, int Y) {
        List<Point> pointsLinked = new ArrayList<>();

        if (p.y != 0 && map[p.x][p.y - 1] == '1') {
            pointsLinked.add(new Point(p.x, p.y - 1));
        }

        if (p.x != 0 && map[p.x - 1][p.y] == '1') {
            pointsLinked.add(new Point(p.x - 1, p.y));
        }

        if (p.y != Y - 1 && map[p.x][p.y + 1] == '1') {
            pointsLinked.add(new Point(p.x, p.y + 1));
        }

        if (p.x != X - 1 && map[p.x + 1][p.y] == '1') {
            pointsLinked.add(new Point(p.x + 1, p.y));
        }

        return pointsLinked;
    }

    private void walk(Point p, char[][] map) {
        map[p.x][p.y] = '2';
    }

    private final static class Point {
        final int x;
        final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
