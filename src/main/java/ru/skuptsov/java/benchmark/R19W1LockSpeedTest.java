package ru.skuptsov.java.benchmark;

import org.openjdk.jmh.annotations.*;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;

/**
 * @author Sergey Kuptsov
 * @since 25/01/2017
 */
@State(value = Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
public class R19W1LockSpeedTest extends BaseRWLockSpeedTest {

    public R19W1LockSpeedTest() {
        super(19, 1);
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public void stampedPessimisticLockRWLockSpeedTest() {
        super.stampedPessimisticLockRWLockSpeedTest();
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public void syncRWLockSpeedTest() {
        super.syncRWLockSpeedTest();
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public void rWReentrantRWLockSpeedTest() {
        super.rWReentrantRWLockSpeedTest();
    }

    @Benchmark
    @BenchmarkMode(value = AverageTime)
    @OutputTimeUnit(value = MICROSECONDS)
    public void stampedOptimisticLockRWLockSpeedTest() {
        super.stampedOptimisticLockRWLockSpeedTest();
    }
}
