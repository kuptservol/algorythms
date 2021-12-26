package puzzler.interview.y;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

public class OptimalRoute {

    // O(N)
    public char[] optimalRoute(char[] directions) {
        LinkedList<PosWithD> route = new LinkedList<>();
        Set<Pos> visited = new HashSet<>();

        int X = 0;
        int Y = 0;
        for (int i = 0; i < directions.length; i++) {
            if (i != 0) {
                switch (directions[i - 1]) {
                    case 'R':
                        X += 1;
                        break;
                    case 'L':
                        X -= 1;
                        break;
                    case 'U':
                        Y += 1;
                        break;
                    case 'D':
                        Y -= 1;
                        break;
                }
            }

            Pos dirPos = new Pos(X, Y);

            // ['R', 'D', 'L', 'U', 'R']
            if (visited.contains(dirPos)) {
                Pos prev = route.peekLast() == null ? null : route.peekLast().pos;
                while (prev != null && !prev.equals(dirPos)) {
                    route.removeLast();
                    visited.remove(prev);
                    prev = route.peekLast() == null ? null : route.peekLast().pos;
                }
                visited.remove(prev);
                route.removeLast();
            }

            visited.add(dirPos);
            route.add(new PosWithD(dirPos, directions[i]));
        }

        char[] ans = new char[route.size()];
        for (int i = 0; i < route.size(); i++) {
            ans[i] = route.get(i).dir;
        }
        return ans;
    }

    class PosWithD {
        Pos pos;
        Character dir;

        public PosWithD(Pos pos, Character dir) {
            this.pos = pos;
            this.dir = dir;
        }
    }

    class Pos {
        int x, y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Pos)) {
                return false;
            }
            Pos pos = (Pos) o;
            return x == pos.x && y == pos.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    @Test
    void test() {
        char[] a = {'R', 'D', 'L', 'U', 'R'};
        char[] b = {'R'};
        Assert.assertEquals(optimalRoute(a), b);

        a = new char[]{'R', 'D', 'L', 'R', 'U', 'U', 'R'};
        b = new char[]{'R', 'U', 'R'};
        Assert.assertEquals(optimalRoute(a), b);

        a = new char[]{'R', 'R'};
        b = new char[]{'R', 'R'};
        Assert.assertEquals(optimalRoute(a), b);

        a = new char[]{'R'};
        b = new char[]{'R'};
        Assert.assertEquals(optimalRoute(a), b);

        a = new char[]{'D', 'R', 'U'};
        b = new char[]{'D', 'R', 'U'};
        Assert.assertEquals(optimalRoute(a), b);
    }
}
