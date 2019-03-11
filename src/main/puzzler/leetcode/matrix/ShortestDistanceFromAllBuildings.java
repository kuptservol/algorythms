package puzzler.leetcode.matrix;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *
 * You want to build a house on an empty land which reaches all buildings in the shortest amount of distance. You can only move up, down,
 * left and right. You are given a 2D grid of values 0, 1 or 2, where:
 *
 * Each 0 marks an empty land which you can pass by freely.
 * Each 1 marks a building which you cannot pass through.
 * Each 2 marks an obstacle which you cannot pass through.
 *
 *
 * For example, given three buildings at (0,0), (0,4), (2,2), and an obstacle at (0,2). The point (1,2) is an ideal empty land to build a
 * house, as the total travel distance of 3+3+1=7 is minimal. So return 7.
 */
public class ShortestDistanceFromAllBuildings {

    int[][] grid1 = {
            {1, 0, 2, 0, 1},
            {0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0}
    };

    int[][] grid2 = {{1, 2, 0}};

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {grid1, 7},
                {grid2, -1}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[][] grid, int result) {
        assertEquals(shortestDistance(grid), result);
    }

    /**
     * for each building count sum distances to it from every point of matrix
     * then find lowest value
     * speed O(num_buildings*matrix_size) mem O(matrix_size)
     */
    public int shortestDistance(int[][] grid) {

        int[][][] distances = new int[grid.length][grid[0].length][2];

        int housesNum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {

                if (grid[i][j] == 2) {
                    distances[i][j][0] = -1;
                }

                if (grid[i][j] == 1) {
                    housesNum++;
                    distances[i][j][0] = -1;

                    // sum distances to (i,j) building with bfs
                    Set<String> visited = new HashSet<>();
                    LinkedList<int[]> bfs = new LinkedList<>();
                    bfs.addLast(new int[]{i + 1, j, 0});
                    bfs.addLast(new int[]{i - 1, j, 0});
                    bfs.addLast(new int[]{i, j + 1, 0});
                    bfs.addLast(new int[]{i, j - 1, 0});

                    int[] next;
                    while ((next = bfs.pollFirst()) != null) {
                        int i_next = next[0];
                        int j_next = next[1];
                        if (i_next < 0 || j_next < 0 || i_next >= grid.length || j_next >= grid[0].length) {
                            continue;
                        }

                        if (visited.contains(i_next + "_" + j_next)) {
                            continue;
                        } else {
                            visited.add(i_next + "_" + j_next);
                        }

                        int prev = next[2] + 1;
                        if (grid[i_next][j_next] == 0) {
                            distances[i_next][j_next][0] += prev;
                            //count num of buildings that reached point
                            distances[i_next][j_next][1]++;

                            bfs.addLast(new int[]{i_next + 1, j_next, prev});
                            bfs.addLast(new int[]{i_next - 1, j_next, prev});
                            bfs.addLast(new int[]{i_next, j_next + 1, prev});
                            bfs.addLast(new int[]{i_next, j_next - 1, prev});
                        }
                    }
                }
            }
        }

        // find min distance pos if not -1
        int min = -1;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                // this point was reached from every bulding
                if (distances[i][j][1] == housesNum) {
                    if (min == -1) {
                        min = distances[i][j][0];
                    } else {
                        min = Math.min(distances[i][j][0], min);
                    }
                }
            }
        }

        return min;
    }
}
