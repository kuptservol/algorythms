package ru.skuptsov;

import com.google.common.util.concurrent.AtomicDouble;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;

/**
 * @author Sergey Kuptsov
 * @since 25/04/2016
 */
@State(value = Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
public class LockSpeedTest {
    private Lock lock = new ReentrantLock();

    private double x = 1;
    private AtomicDouble atomicDouble = new AtomicDouble();
    private DoubleAdder doubleAdder = new DoubleAdder();

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LockSpeedTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    private double method() {
        return x++;
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
    public double syncSingleThread() {
        synchronized (this) {
            return method();
        }
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public double atomicDouble() {
        return atomicDouble.addAndGet(1);
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public double doubleAdder() {
        doubleAdder.add(1d);
        return doubleAdder.doubleValue();
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
}
