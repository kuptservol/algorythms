package java_.reactive_streams;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author Sergey Kuptsov
 */
public class ReactiveStreamsTests {

    @Test
    public void simpleFlux() {
        Flux<String> flux = Flux.just("One");

        flux.subscribe(System.out::print);
    }

    @Test
    public void asyncMap() throws InterruptedException {
        Flux<Integer> flux = Flux.fromStream(IntStream.range(1, 1000).boxed()
                .map(i -> {
                    System.out.println("Producing next: " + i);
                    return i;
                }));

        Flux<Integer> transformFlux = flux.flatMap(s -> Mono.defer(() -> {
            System.out.println("Heavy Operation for:" + s);
            sleep(1000);
            return Mono.just(s);
        })).map(s -> {
            System.out.println("Can process " + s + " already!");
            return s;
        });

        System.out.println("Now consuming");
        transformFlux.subscribe(System.out::println);
    }

    @Test
    public void asyncProduceMap() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1000);
        Flux<Integer> flux = Flux.fromStream(IntStream.range(1, 1000).boxed()
                .map(i -> {
                    System.out.println("Producing next: " + i);
                    return i;
                }))
                .subscribeOn(Schedulers.elastic());

        Flux<Integer> transformFlux = flux.flatMap(s -> Mono.defer(() -> {
            System.out.println("Heavy Operation for:" + s);
            sleep(1000);
            return Mono.just(s);
        })).map(s -> {
            System.out.println("Can process " + s + " already!");
            return s;
        });

        System.out.println("Now consuming");
        transformFlux.subscribe((v) ->
        {
            System.out.println(v);
            latch.countDown();
        });
        System.out.println("SUBSCRIBING ASYNC");
        latch.await();
        System.out.println("SUBSCRIBING FINISHED");
    }

    @Test
    public void asyncSlowConsumerMap() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1000);
        Flux<Integer> flux = Flux.fromStream(IntStream.range(1, 1000).boxed()
                .map(i -> {
                    System.out.println("Producing next: " + i);
                    return i;
                }));

        Flux<Integer> transformFlux = flux
                .publishOn(Schedulers.parallel())
                .flatMap(s -> Mono.defer(() -> {
                    System.out.println("Heavy Operation for:" + s);
                    sleep(1000);
                    return Mono.just(s);
                }));

        System.out.println("Now consuming");
        transformFlux.subscribe((v) ->
        {
            System.out.println("Consumed: " + v);
            latch.countDown();
        });
        System.out.println("SUBSCRIBING ASYNC");
        latch.await();
        System.out.println("SUBSCRIBING FINISHED");
    }

    @Test
    public void asyncSlowProducerMap() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1000);
        Flux<Integer> flux = Flux.fromStream(IntStream.range(1, 1000).boxed()
                .map(i -> {
                    sleep(1000);
                    System.out.println("Producing heavy next: " + i);
                    return i;
                }));

        Flux<Integer> transformFlux = Flux.defer(() -> flux
                .flatMap(s -> {
                    System.out.println("Light Operation for:" + s);
                    return Mono.just(s);
                }));

        System.out.println("Now consuming");
        transformFlux
                .subscribeOn(Schedulers.elastic())
                .subscribe((v) ->
                {
                    System.out.println("Consumed: " + v);
                    latch.countDown();
                });
        System.out.println("SUBSCRIBING ASYNC");
        latch.await();
        System.out.println("SUBSCRIBING FINISHED");
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
