package ru.skuptsov.puzzlers.job.interview.codility.toptal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 28/03/2017
 * Given an infinitely large chessboard and a pair of coordinates (x, y),
 * find the minimum number of moves it takes for a knight at position (0, 0) to get to that position.
 * Return -1 if it's not possible for the knight to get there.
 */
public class Question_3 {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {4, 5, 3},
                {1, 0, 3}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int x, int y, int res) {
        assertEquals(solution(x, y), res);
    }

    /*
    Very very poor bfs problem solution but at least. Probably can't pass any performance.
       I know there is about O(1) solution
     */
    public int solution(int A, int B) {

        int currMinHops = -1;

        LinkedList<WalkNode> walkNodeQueue = new LinkedList<>();
        Set<WalkNode> walkedSet = new HashSet<>();

        walkNodeQueue.add(new WalkNode(0, 0, 0));
        double xLimit = Math.pow(A, 2);
        double yLimit = Math.pow(B, 2);

        WalkNode walkNode;
        while ((walkNode = walkNodeQueue.pollFirst()) != null) {
            int currHop = walkNode.hop;

            if (!walkedSet.contains(walkNode)) {
                walkedSet.add(walkNode);


                int x = walkNode.x;
                int y = walkNode.y;
                if (!(xLimit + 9 < Math.pow(x, 2)) && !(yLimit + 9 < Math.pow(y, 2) + 9)) {

                    if (currMinHops > 100000000) {
                        return -2;
                    }

                    if (x == A && y == B) {
                        if (currMinHops == -1) {
                            currMinHops = currHop;
                        } else {
                            currMinHops = Math.min(currHop, currMinHops);
                        }
                    } else {
                        currHop++;
                        walkNodeQueue.add(new WalkNode(x + 1, y + 2, currHop));
                        walkNodeQueue.add(new WalkNode(x + 1, y - 2, currHop));
                        walkNodeQueue.add(new WalkNode(x - 1, y + 2, currHop));
                        walkNodeQueue.add(new WalkNode(x - 1, y - 2, currHop));

                        walkNodeQueue.add(new WalkNode(x + 2, y + 1, currHop));
                        walkNodeQueue.add(new WalkNode(x + 2, y - 1, currHop));
                        walkNodeQueue.add(new WalkNode(x - 2, y + 1, currHop));
                        walkNodeQueue.add(new WalkNode(x - 2, y - 1, currHop));
                    }
                }
            }
        }

        return currMinHops;
    }


    private final class WalkNode {
        int x, y, hop;

        public WalkNode(int x, int y, int hop) {
            this.x = x;
            this.y = y;
            this.hop = hop;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WalkNode walkNode = (WalkNode) o;
            return x == walkNode.x &&
                    y == walkNode.y;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(x, y);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("x", x)
                    .add("y", y)
                    .add("hop", hop)
                    .toString();
        }
    }
}
