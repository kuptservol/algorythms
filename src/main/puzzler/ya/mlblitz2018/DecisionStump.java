package puzzler.ya.mlblitz2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * @author Sergey Kuptsov
 *         https://contest.yandex.ru/contest/8470/problems/A/
 */
public class DecisionStump {

    /**
     * Divide function on two function with horiz lines
     * Trying to split points on two spaces between every 2 sorted by x points
     * <p>
     * For each:
     * Trying to find minima of cost function:
     * [SUMM(f(x) - yi)^2]` == 0 ->  2*SUMM(f(x) - yi) == 0 ->
     * a*x * |Y| = SUMM(yi) ->  a = SUMM(yi)/|Y|
     * <p>
     * So best guess of horizontal line regression is mean of Y values on
     */
    public static void main(String[] args) throws IOException {
        List<String> inLines = Files.readAllLines(Paths.get("stump.in"));

        String next = inLines.get(0);
        int n = Integer.valueOf(next);

        List<Point> ePoints = new ArrayList<>();

        SSE aSSE = new SSE();
        SSE bSSE = new SSE();

        for (int i = 1; i <= n; i++) {
            String[] inSplitted = inLines.get(i).split(" ");

            Integer y = Integer.valueOf(inSplitted[1]);
            Integer x = Integer.valueOf(inSplitted[0]);
            ePoints.add(Point.cons(x, y));
            bSSE.add(y);
        }

        ePoints.sort(Comparator.comparingInt(o -> o.x));

        double aBest = 0, bBest = bSSE.mean.mean, cBest = ePoints.get(0).x, costBest = bSSE.M;

        for (int i = 0; i < ePoints.size() - 1; i++) {
            Point p = ePoints.get(i);
            Point pnext = ePoints.get(i + 1);

            aSSE.add(p.y);
            bSSE.minus(p.y);

            if (p.x == pnext.x)
                continue;

            double cost = aSSE.M + bSSE.M;

            if (cost < costBest) {
                aBest = aSSE.mean.mean;
                bBest = bSSE.mean.mean;
                costBest = cost;
                cBest = (p.x + pnext.x) / 2.0;
            }
        }

        String format = String.format(Locale.US, "%.6f %.6f %.6f", aBest, bBest, cBest);

        Files.write(Paths.get("stump.out"), format.getBytes());
    }

    /**
     * calculating mean with sum cache optimization
     */
    public static class Mean {
        double mean;
        int count;

        public Mean(double mean, int count) {
            this.mean = mean;
            this.count = count;
        }

        double add(double x) {
            count += 1;
            mean += (x - mean) / count;
            return mean;
        }

        double minus(double x) {
            count -= 1;
            mean += (mean - x) / count;
            return mean;
        }
    }

    /**
     * calculating variance with Welford's optimization
     */
    public static class SSE {
        Mean mean = new Mean(0, 0);
        double M = 0;

        double add(double x) {
            double diff = x - mean.mean;
            double meanN = mean.add(x);
            M += diff * (x - meanN);
            return M;
        }

        double minus(double x) {
            double diff = x - mean.mean;
            double meanN = mean.minus(x);
            M += diff * (meanN - x);
            return M;
        }
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
