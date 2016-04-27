package ru.skuptsov.performance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;

/**
 * @author Sergey Kuptsov
 * @since 25/04/2016
 */
@State(value = Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
public class LockSpeedTest {
    Lock lock = new ReentrantLock();

    double x = 1;
    double y = 2;


    public double method() {
        return (x + y);
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public double simple() {
        return method();
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public double sync() {
        synchronized (this) {
            return method();
        }
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public double lock() {
        lock.lock();
        double result = method();
        lock.unlock();
        return result;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LockSpeedTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
