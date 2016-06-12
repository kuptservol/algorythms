package ru.skuptsov;

import org.openjdk.jmh.annotations.*;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;

/**
 * @author Sergey Kuptsov
 * @since 12/06/2016
 */
@State(value = Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
public class CounterSpeedTest {

    public static final int COUNT = 1000000;
    private double x_double = 1;
    private long x_long = 1;

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public void doubleCounter() {
        for (int i = 0; i < COUNT; i++) {
            x_double++;
        }
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public void intCounter() {
        for (int i = 0; i < COUNT; i++) {
            x_long++;
        }
    }
}
