package puzzler.leetcode.matrix;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a 2D board containing 'X' and 'O' (the letter O), capture all regions surrounded by 'X'.
 * <p>
 * A region is captured by flipping all 'O's into 'X's in that surrounded region.
 * <p>
 * For example,
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * After running your function, the board should be:
 * <p>
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 */
public class SurroundedRegions {

    char[][] board1 = {
            {'X', 'X', 'X', 'X'},
            {'X', 'O', 'O', 'X'},
            {'X', 'X', 'O', 'X'},
            {'X', 'O', 'X', 'X'}
    };

    char[][] result1 = {
            {'X', 'X', 'X', 'X'},
            {'X', 'X', 'X', 'X'},
            {'X', 'X', 'X', 'X'},
            {'X', 'O', 'X', 'X'}
    };

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {board1, result1}
        };
    }

    @Test(dataProvider = "testData")
    public void test(char[][] board, char[][] result) {
        solve(board);
        print(board);
        System.out.println(System.lineSeparator());
        System.out.println("result");
        print(result);
        assertEquals(board, result);
    }

    private void print(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            System.out.println(System.lineSeparator());
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j]);
                System.out.print(',');
            }
        }
    }

    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }

        int X = board.length;
        int Y = board[0].length;

        for (int j = 0; j < Y; j++) {
            char next = board[0][j];
            if (next == 'O') {
                walk(board, 0, j, X, Y);
            }
        }

        for (int j = 0; j < Y; j++) {
            char next = board[X - 1][j];
            if (next == 'O') {
                walk(board, X - 1, j, X, Y);
            }
        }

        for (int i = 0; i < X; i++) {
            char next = board[i][0];
            if (next == 'O') {
                walk(board, i, 0, X, Y);
            }
        }

        for (int i = 0; i < X; i++) {
            char next = board[i][Y - 1];
            if (next == 'O') {
                walk(board, i, Y - 1, X, Y);
            }
        }

        for (int k = 0; k < X; k++) {
            for (int l = 0; l < Y; l++) {
                if (board[k][l] == 'O')
                    board[k][l] = 'X';

                if (board[k][l] == '!')
                    board[k][l] = 'O';
            }
        }
    }

    private void walk(char[][] board, int i, int j, int X, int Y) {

        Point start = Point.cons(i, j);
        LinkedList<Point> list = new LinkedList<>();
        list.add(start);
        Point p;
        while ((p = list.pollFirst()) != null) {
            board[p.x][p.y] = '!';
            list.addAll(getLinked(p, board, X, Y));
        }
    }

    public List<Point> getLinked(Point p, char[][] map, int X, int Y) {
        List<Point> pointsLinked = new ArrayList<>();

        if (p.y != 0 && map[p.x][p.y - 1] == 'O') {
            pointsLinked.add(new Point(p.x, p.y - 1));
        }

        if (p.x != 0 && map[p.x - 1][p.y] == 'O') {
            pointsLinked.add(new Point(p.x - 1, p.y));
        }

        if (p.y != Y - 1 && map[p.x][p.y + 1] == 'O') {
            pointsLinked.add(new Point(p.x, p.y + 1));
        }

        if (p.x != X - 1 && map[p.x + 1][p.y] == 'O') {
            pointsLinked.add(new Point(p.x + 1, p.y));
        }

        return pointsLinked;
    }


    private final static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        static Point cons(int x, int y) {
            return new Point(x, y);
        }
    }
}
