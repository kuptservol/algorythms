package puzzler.interview;

/**
 * Created by Сергей on 07.04.2016.
 */
public class Fibonacci {


    private final int N;

    public Fibonacci(int N) {
        this.N = N;
    }

    public static void main(String[] args) {

        Fibonacci fibonacci = new Fibonacci(Integer.valueOf(args[0]));
        System.out.println(fibonacci.fibR(8));
    }

    private int fibR(int x) {
        int r = x > 2 ? fibR(x - 1) + fibR(x - 2) : 1;
        System.out.println(r);
        return r;
    }


    private int fibC() {

        int prev = 0;
        int next = 1;
        int x;
        System.out.println(0);
        while (next < N) {
            x = next;
            System.out.println(next);
            next = prev + next;
            prev = x;
        }

        return prev;
    }

}
