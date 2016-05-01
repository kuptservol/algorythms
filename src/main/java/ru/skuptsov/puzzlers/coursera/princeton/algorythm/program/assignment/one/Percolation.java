package ru.skuptsov.puzzlers.coursera.princeton.algorythm.program.assignment.one;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


/**
 * Created by SKuptsov on 16.02.2016.
 * <p>
 * Memory usage :
 * Site : 16 + 4 + 4 + 4 + 8 + 1
 * PercolationSystem : 16 + 4 + 4 + 4 + 24 + 32*N + 8*N^2 + padding
 * Percolation : 16 + 8 + 8
 * WeightedQuickUnionUF : 16 + 4 + 24 + 4*N + 24 + 4*N + padding
 */
public class Percolation {
    private final WeightedQuickUnionUF pathTree;
    private final PercolationSystem system;

    private PercolationSystem.Site topVirtualSite;
    private PercolationSystem.Site bottomVirtualSite;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        int NWithVirtualLevels = N + 2;
        system = new PercolationSystem(NWithVirtualLevels);
        pathTree = new WeightedQuickUnionUF(NWithVirtualLevels * NWithVirtualLevels);

        topVirtualSite = system.get(0, 0);
        system.open(topVirtualSite);
        bottomVirtualSite = system.get(NWithVirtualLevels - 1, NWithVirtualLevels - 1);
        system.open(bottomVirtualSite);
    }

    public static void main(String[] args) {

        Percolation percolation = new Percolation(1);


        //int[] testInput = {2, 0, 0, 1, 2, 2, 2, 2, 0, 1, 2, 0, 1, 2, 0, 1, 0, 0, 2, 1, 0, 0, 0, 1, 2, 2, 0, 2};
        int[] testInput = {0,0};

        for (int i = 0; i < testInput.length - 1; i += 2) {
            percolation.open(testInput[i] + 1, testInput[i + 1] + 1);
        }

        System.out.println(percolation.percolates());
//        System.out.println(percolation.isFull(1, 2));
//        System.out.println(percolation.isOpen(1, 2));
    }

    public void open(int i, int j) {
        checkBounds(i, j);

        PercolationSystem.Site site = system.get(i, j);
        system.open(site);

        if (system.isTopRow(site)) {
            union(site, topVirtualSite);
        }
        if (system.isBottomRow(site)) {
            union(site, bottomVirtualSite);
        }

        PercolationSystem.Site left = system.getLeft(site);
        connectIfPresent(site, left);

        PercolationSystem.Site right = system.getRight(site);
        connectIfPresent(site, right);

        PercolationSystem.Site up = system.getUp(site);
        connectIfPresent(site, up);

        PercolationSystem.Site bottom = system.getBottom(site);
        connectIfPresent(site, bottom);

    }

    private void connectIfPresent(PercolationSystem.Site site1, PercolationSystem.Site site2) {
        if (site2.ifPresent())
            if (site2.isOpen()) {
                union(site1, site2);
            }
    }

    private void union(PercolationSystem.Site site1, PercolationSystem.Site site2) {
        pathTree.union(
                system.getPosition(site2),
                system.getPosition(site1));
    }

    public boolean isFull(int i, int j) {
        checkBounds(i, j);

        PercolationSystem.Site site = system.get(i, j);

        return site.isOpen() && pathTree.connected(system.getPosition(site), system.getPosition(topVirtualSite));

    }

    public boolean isOpen(int i, int j) {
        checkBounds(i, j);

        return system.get(i, j).isOpen();
    }

    public boolean percolates() {

        return pathTree.connected(system.getPosition(bottomVirtualSite), system.getPosition(topVirtualSite));
    }

    private void checkBounds(int x, int y) {
        if (system.isOutOfBound(x, y)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static final class PercolationSystem {

        private static final int EMPTY_VALUE = -1;
        private static final int OPEN_VALUE = 1;
        private final int N;
        private final int[][] matrix;

        private PercolationSystem(int N) {
            this.N = N;
            matrix = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    matrix[i][j] = EMPTY_VALUE;
                }
            }

        }

        public int getPosition(Site site) {
            return site.x * N + site.y;
        }

        public Site get(int x, int y) {
            return new Site(x, y, matrix[x][y]);
        }

        public Site getRight(Site site) {
            return getNeighbour(site.x, site.y + 1);
        }

        public Site getLeft(Site site) {
            return getNeighbour(site.x, site.y - 1);
        }

        public Site getUp(Site site) {
            return getNeighbour(site.x - 1, site.y);
        }

        public Site getBottom(Site site) {
            return getNeighbour(site.x + 1, site.y);
        }

        public boolean isTopRow(Site site) {
            return site.x == 1;
        }

        public boolean isBottomRow(Site site) {
            return site.x == N - 2;
        }

        private Site getNeighbour(int x, int y) {
            if (isOutOfBound(x, y))
                return Site.EMPTY;
            else return get(x, y);
        }

        public void open(Site site) {
            matrix[site.x][site.y] = OPEN_VALUE;
        }

        private boolean isOutOfBound(int x, int y) {
            return (x < 1 || x > N-2 || y < 1 || y > N-2);
        }

        private static final class Site {
            private static final Site EMPTY = new Site();
            private final boolean present;
            //0..N-1
            private int x, y;
            private int value = EMPTY_VALUE;

            private Site() {
                present = false;
            }

            private Site(int x, int y, int value) {
                this.y = y;
                this.x = x;
                this.value = value;
                present = true;
            }

            public boolean isOpen() {
                return value == OPEN_VALUE;
            }

            public boolean ifPresent() {
                return present;
            }
        }
    }
}
