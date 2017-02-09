package ru.skuptsov.algorythms.unionfind;

import ru.skuptsov.algorythms.unionfind.common.UnionFind;
import ru.skuptsov.algorythms.utils.IOUtils;

import java.io.FileNotFoundException;

/**
 * Created by Сергей on 03.02.2016.
 */
public class WeightedQuickUnion extends UnionFind {

    int tS[];

    public WeightedQuickUnion(int N) {
        super(N);
        tS = new int[N];

        for (int i = 0; i < N; i++) {
            tS[i] = 1;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        IOUtils io = IOUtils.createFromFilePath("tinyUF.txt");

        int N = io.readInt();
        WeightedQuickUnion uf = new WeightedQuickUnion(N);
        while (!io.isEmpty()) {
            int p = io.readInt();
            int q = io.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            System.out.println(p + " " + q);
        }
        io.println(uf.count() + " components");

        io.print(uf.id);
    }

    @Override
    protected int find(int p) {

        while (id[p] != p)
            p = id[p];
        return p;
    }

    @Override
    protected void union(int p, int q) {

        int rootP = find(p);
        int rootQ = find(q);

        if (rootP != rootQ) {

            if (tS[rootP] < tS[rootQ]) {
                id[rootP] = rootQ;
                tS[rootQ] += tS[rootP];
            } else {
                id[rootQ] = rootP;
                tS[rootP] += tS[rootQ];
            }
            count--;
        }

    }
}
