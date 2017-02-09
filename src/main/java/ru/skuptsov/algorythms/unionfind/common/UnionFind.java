package ru.skuptsov.algorythms.unionfind.common;

/**
 * Created by Сергей on 02.02.2016.
 */
public abstract class UnionFind {

    protected int[] id;
    protected int count;

    public UnionFind(int N) {

        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
        count = N;
    }

    public int count() {
        return count;
    }

    protected boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    protected abstract int find(int p);

    protected abstract void union(int p, int q);

}
