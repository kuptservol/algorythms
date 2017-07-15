package ru.skuptsov.puzzlers.codingame;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;


public class BenderRegression {

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

        Map<Complexity, Long> sko = new HashMap<>();
        Arrays.stream(Complexity.values()).forEach(cty -> sko.put(cty, 0L));

        for (int i = 0; i < x.length - 1; i++) {
            final int j = i;
            System.out.println(x[j] + ", " + y[j] + ", " + getModelValue(x[j], x[0], y[0], Complexity.O_1).longValue() + ", " + getModelValue(x[j], x[0], y[0], Complexity.O_log_N).longValue());
//            Arrays.stream(Complexity.values())
//                    .forEach(cty ->
//                            sko.put(cty, sko.get(cty).add(diff(y[j], getModelValue(x[j], x[0], y[0], cty)))));
        }

        Optional<Map.Entry<Complexity, Long>> least = sko.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .findFirst();

        System.out.println(least.get().getKey().getName());
    }

    private static BigDecimal getModelValue(int x, int x0, int y0, Complexity cty) {
        return BigDecimal.valueOf(y0)
                .divide(cty.getTransformFunction().apply(x0), BigDecimal.ROUND_FLOOR)
                .multiply(cty.getTransformFunction().apply(x));
    }

    private static Long diff(Integer one, BigDecimal second) {
        return (BigDecimal.valueOf(one).subtract(second)).pow(2).longValue();
    }

    private enum Complexity {
        O_1("O(1)", x -> BigDecimal.valueOf(1)),
        O_log_N("O(log n)", x -> BigDecimal.valueOf(Math.log(x)));
//        O_n("O(n)", BigInteger::valueOf),
//        O_n_log_N("O(n log n)", x -> BigInteger.valueOf(x).multiply(BigInteger.valueOf((long) Math.log(x)))),
//        O_n_2("O(n^2)", x -> BigInteger.valueOf(x).pow(2)),
//        O_n_2_log_N("O(n^2 log n)", x -> (BigInteger.valueOf(x).pow(2)).multiply(BigInteger.valueOf((long) Math.log(x)))),
//        O_n_3("O(n^3)", x -> BigInteger.valueOf(x).pow(3)),
//        O_2_n("O(2^n)", x -> BigInteger.valueOf(2).pow(x));

        private final Function<Integer, BigDecimal> transformFunction;
        private final String name;

        public Function<Integer, BigDecimal> getTransformFunction() {
            return transformFunction;
        }

        public String getName() {
            return name;
        }

        Complexity(String name, Function<Integer, BigDecimal> transformFunction) {
            this.name = name;
            this.transformFunction = transformFunction;
        }
    }
}
