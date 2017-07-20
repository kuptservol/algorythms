package puzzler.interview.upwork;

/**
 * @author Sergey Kuptsov
 * @since 19/03/2017
 */
public class Network {

    private final int size;
    private final int[] net;
    private final int[] netSize;

    public Network(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size cannot be less than 1");
        }
        this.size = size;
        net = new int[size];
        netSize = new int[size];
        for (int i = 0; i < size; i++) {
            net[i] = i;
            netSize[i] = 1;
        }
    }

    private int findRoot(int p) {
        while (p != net[p]) {
            p = net[p];
        }

        return p;
    }

    public boolean query(int x, int y) {
        checkBounds(x);
        checkBounds(y);

        return net[findRoot(x - 1)] == net[findRoot(y - 1)];
    }

    public void connect(int p, int q) {
        checkBounds(p);
        checkBounds(q);

        int pRoot = findRoot(p - 1);
        int qRoot = findRoot(q - 1);

        if (qRoot != pRoot) {
            if (netSize[pRoot] < netSize[qRoot]) {
                net[pRoot] = qRoot;
                netSize[qRoot] += netSize[pRoot];
            } else {
                net[qRoot] = pRoot;
                netSize[pRoot] += netSize[qRoot];
            }
        }
    }

    private void checkBounds(int num) {
        if (num <= 0 || num > size) {
            throw new IllegalArgumentException("Value cannot be less than 1 and bigger than size");
        }
    }
}
