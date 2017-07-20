package algorithm.unionfind;

import java.io.FileNotFoundException;

import algorithm.unionfind.common.UnionFind;
import algorithm.utils.IOUtils;

/**
 * Created by Сергей on 03.02.2016.
 */

/**
 * O(N^2)
 */
public class QuickUnion extends UnionFind {

    public QuickUnion(int N) {
        super(N);
    }

    public static void main(String[] args) throws FileNotFoundException {

        IOUtils io = IOUtils.createFromFilePath("tinyUF2.txt");

        int N = io.readInt();
        QuickUnion uf = new QuickUnion(N);
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
        while (id[p] != p) {
            p = id[p];
        }

        return p;
    }

    @Override
    protected void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (qRoot != pRoot) {
            id[pRoot] = qRoot;
            count--;
        }
    }
}
