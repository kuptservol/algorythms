package ru.skuptsov.puzzlers.codingame;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
public class BenderRegression {

    public static long LINEAR_CONSTANT = 1;

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        int N = in.nextInt();

        int[] x = new int[N];
        int[] y = new int[N];
        for (int i = 0; i < N; i++) {
            int num = in.nextInt();
            x[i] = num;
            int t = in.nextInt();
            y[i] = t;
        }

        Map<Complexity, BigInteger> sko = new HashMap<>();
        Arrays.stream(Complexity.values()).forEach(cty -> sko.put(cty, BigInteger.valueOf(0)));
        LINEAR_CONSTANT = y[0];

        for (int i = 0; i < x.length - 1; i++) {
            final int j = i;
            Arrays.stream(Complexity.values())
                    .forEach(cty ->
                            sko.put(cty, sko.get(cty).add(diff(y[j], cty.getTransformFunction().apply(x[j])))));
        }

        Optional<Map.Entry<Complexity, BigInteger>> least = sko.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .findFirst();

        System.out.println(least.get().getKey().getName());
    }

    private static BigInteger diff(Integer one, BigInteger second) {
        return BigInteger.valueOf(one).subtract(second).pow(2);
    }

    private enum Complexity {
        O_1("O(1)", x -> BigInteger.valueOf(LINEAR_CONSTANT)),
        O_log_N("O(log n)", x -> BigInteger.valueOf((long) Math.log(x))),
        O_n("O(n)", BigInteger::valueOf),
        O_n_log_N("O(n log n)", x -> BigInteger.valueOf(x).multiply(BigInteger.valueOf((long) Math.log(x)))),
        O_n_2("O(n^2)", x -> BigInteger.valueOf(x).pow(2)),
        O_n_2_log_N("O(n^2 log n)", x -> BigInteger.valueOf(x).pow(2).multiply(BigInteger.valueOf((long) Math.log(x)))),
        O_n_3("O(n^3)", x -> BigInteger.valueOf(x).pow(3)),
        O_2_n("O(2^n)", x -> BigInteger.valueOf(2).pow(x));

        private final Function<Integer, BigInteger> transformFunction;
        private final String name;

        public Function<Integer, BigInteger> getTransformFunction() {
            return transformFunction;
        }

        public String getName() {
            return name;
        }

        Complexity(String name, Function<Integer, BigInteger> transformFunction) {
            this.name = name;
            this.transformFunction = transformFunction;
        }
    }
}
