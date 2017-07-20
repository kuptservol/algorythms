package algorithm.unionfind;

import java.io.FileNotFoundException;

import algorithm.unionfind.common.UnionFind;
import algorithm.utils.IOUtils;

/**
 * Created by Сергей on 02.02.2016.
 */
public class QuickFind extends UnionFind {

    public QuickFind(int N) {
        super(N);
    }

    public static void main(String[] args) throws FileNotFoundException {

        IOUtils io = IOUtils.createFromFilePath("tinyUF.txt");

        int N = io.readInt();
        QuickFind uf = new QuickFind(N);
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
    public int find(int p) {
        return id[p];
    }

    @Override
    public void union(int p, int q) {
        int pID = find(p);
        int qID = find(q);

        if (pID == qID) return;

        for (int i = 0; i < id.length; i++)
            if (id[i] == pID) id[i] = qID;
        count--;
    }
}
