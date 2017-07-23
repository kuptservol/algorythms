package puzzler.coursera.prinston.algorithm;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by Skuptsov on 23.02.2016.
 * Complexity is
 * number of experiments * N^2 Percolation constructor * worst case N^2-N iterations * percolates() complexity = 2*log(N)
 * O(T*N^2*(N^2-N)*(2*log(N)))
 */
public class PercolationStats {

    private final int T;
    private double[] experiments;

    public PercolationStats(int N, int T) {
        if (N < 0 || T < 0)
            throw new IllegalArgumentException();

        this.T = T;

        experiments = new double[T];
        runExperiments(T, N);
    }

    public static void main(String[] args) {

        int N = StdIn.readInt();
        int T = StdIn.readInt();

        PercolationStats percolationStats = new PercolationStats(N, T);
        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95 % confidence interval = "
                + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());

    }

    private void runExperiments(int t, int N) {

        Stopwatch timer = new Stopwatch();

        for (int i = 0; i < t; i++) {

            Percolation percolation = new Percolation(N);
            int j = 0;
            while (!percolation.percolates()) {
                percolation.open(1 + StdRandom.uniform(N), 1 + StdRandom.uniform(N));
                j++;
            }

            experiments[i] = (double) j / (N * N);
        }

        System.out.println(timer.elapsedTime());
    }

    public double mean() {

        return StdStats.mean(experiments);

    }

    public double stddev() {
        return StdStats.stddev(experiments);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
