package ru.skuptsov.benchmark;

import org.openjdk.jmh.annotations.*;

import java.math.BigDecimal;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;

/**
 * @author Sergey Kuptsov
 * @since 12/06/2016
 * <p>
 * Results :
 * Benchmark                           Mode  Cnt     Score    Error  Units
 * CounterSpeedTest.bigDecimalCounter  avgt   30  2886,023 ± 34,953  us/op
 * CounterSpeedTest.doubleCounter      avgt   30   972,527 ± 11,646  us/op
 * CounterSpeedTest.floatCounter       avgt   30   981,762 ± 15,295  us/op
 * CounterSpeedTest.intCounter         avgt   30    42,014 ±  0,876  us/op
 * CounterSpeedTest.longCounter        avgt   30    60,774 ±  1,135  us/op
 */
@State(value = Scope.Benchmark)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
public class CounterSpeedTest {

    private static final int COUNT = 1000000;
    private double x_double = 1;
    private long x_long = 1;
    private int x_int = 1;
    private BigDecimal x_big_decimal = new BigDecimal(0);
    private float x_float = 1;


    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public double doubleCounter() {
        double tmp = 0;
        for (int i = 0; i < COUNT; i++) {
            tmp = x_double++;
        }
        return tmp;
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public int intCounter() {
        int tmp = 0;
        for (int i = 0; i < COUNT; i++) {
            tmp = x_int++;
        }
        return tmp;
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public long longCounter() {
        long tmp = 0;
        for (int i = 0; i < COUNT; i++) {
            tmp = x_long++;
        }
        return tmp;
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public float floatCounter() {
        float tmp = 0;
        for (int i = 0; i < COUNT; i++) {
            tmp = x_float++;
        }

        return tmp;
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public BigDecimal bigDecimalCounter() {
        BigDecimal add = new BigDecimal(0);
        for (int i = 0; i < COUNT; i++) {
            add = x_big_decimal.add(new BigDecimal(1));
        }

        return add;
    }
}
