package ru.skuptsov.puzzlers.job.interview.leetcode.matrix;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {}
        };
    }

//    @Test(dataProvider = "testData")
//    public void test(Object a, Object b) {
//        assertEquals(testMethod(a), b);
//    }

    public int numberOfIslands(int[][] map) {
        int islandsNum = 1;

        int N = map.length;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int p = map[i][j];

                if (p == 0 || p == 2) {
                    continue;
                }

                if (decideIfIsland(i, j, map, N)) {
                    islandsNum++;
                }
            }
        }

        return islandsNum;
    }

    private final static class Point {
        final int x;
        final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private boolean decideIfIsland(int i, int j, int[][] map, int N) {
        LinkedList<Point> walkQ = new LinkedList<>();
        boolean isIsland = true;

        Point start = new Point(i, j);
        walkQ.add(start);

        Point next;
        while ((next = walkQ.pollFirst()) != null) {
            walk(next, map);

            if (isOnBound(next, N))
                isIsland = false;


            for (Point point : getLinked(next, map)) {
                walkQ.add(point);
            }
        }

        return isIsland;
    }

    public List<Point> getLinked(Point p, int[][] map) {
        List<Point> pointsLinked = new ArrayList<>();

        if (p.y != 0 && map[p.x][p.y - 1] == 1) {
            pointsLinked.add(new Point(p.x, p.y - 1));
        }

        if (p.x != 0 && map[p.x - 1][p.y] == 1) {
            pointsLinked.add(new Point(p.x - 1, p.y));
        }

        if (p.y != map.length - 1 && map[p.x][p.y + 1] == 1) {
            pointsLinked.add(new Point(p.x, p.y + 1));
        }

        if (p.x != map.length - 1 && map[p.x + 1][p.y] == 1) {
            pointsLinked.add(new Point(p.x + 1, p.y));
        }

        return pointsLinked;
    }

    public boolean isOnBound(Point p, int N) {
        return p.x == 0 || p.y == 0 || p.x == N - 1 || p.y == N - 1;
    }

    private void walk(Point p, int[][] map) {
        map[p.x][p.y] = 2;
    }

    public static void main(String[] args) {
        NumberOfIslands numberOfIslands = new NumberOfIslands();

        int[][] map = {
                {0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };

        int numOfIslands = numberOfIslands.numberOfIslands(map);

        System.out.println(numOfIslands);
        assertEquals(numOfIslands, 1);
    }
}
