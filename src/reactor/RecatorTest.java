package reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class RecatorTest {

    public static void main(String[] args) throws IOException, InterruptedException {
//		Flux.range(0, 10).map(Math::incrementExact).map(Math::decrementExact).subscribe(System.out::println);
//        mapVsFlatMap();
//        test1();
        Flux.interval(Duration.ofMillis(250))
                .map(input -> {
                    if (input < 3) return "tick " + input;
                    throw new RuntimeException("boom");
                })
                .retry(1)
                .elapsed()
                .subscribe(System.out::println, System.err::println);

        Thread.sleep(210000);


        /*
         * Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9,
         * 10).delayElements(Duration.ofSeconds(1)).publishOn(Schedulers.boundedElastic(
         * )).flatMap(s -> { System.out.println("element -> " + s);
         * System.out.println(Thread.currentThread().getName()); return Flux.empty();
         * }).log().subscribe();
         */
        /*
         * Flux<Integer> flux = Flux.range(0, 10)
         * .publishOn(Schedulers.boundedElastic()) .map(i -> {
         * System.out.println("Mapping for " + i + " is done by thread " +
         * Thread.currentThread().getName()); return i; }); flux.subscribe(s -> {
         * System.out.println("Received " + s + " via " +
         * Thread.currentThread().getName()); });
         */

//		source.subscribe();
//		source.subscribe(d -> System.out.println("Subscriber 2: " + d + " " + Thread.currentThread().getName()+"  "+new Date()));
        System.out.println(Thread.currentThread() + " ended");
        System.in.read();
    }

    private static int countN(int i) {

        return 2;
    }

    public static Mono<List<Integer>> monoList() {
        List<Integer> lists = Arrays.asList(3, 4, 5, 6, 7);
        return Mono.just(lists);
    }

    public static void mapVsFlatMap() {
        Flux<Integer> flux = Flux.just(0, 1, 2, 3);

        Flux<Integer> integerFlux = flux
                .flatMap(e -> Mono.just(e + 1));
        Flux<Mono<Integer>> map = flux
                .map(e -> Mono.just(e * 2));
        Flux<Integer> integerFlux1 = map.flatMap(integerMono -> integerMono);
        Flux<Mono<Integer>> map1 = map.map(integerMono -> integerMono);
    }

    public static void test1() {

        Flux<Integer> flux = Flux.range(1, 5)
//                .delayElements(Duration.ofMillis(200))
                .filter(e -> e % 2 == 0)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(integer -> {
                    return Flux.range(integer, 2);
                })
                .map(e -> {
                    System.out.println(Thread.currentThread().getName() + " map1 : " + e + "  " +
                            new Date());
                    return e * 2;
                });
        System.out.println("in middle : " + Thread.currentThread());
        flux
//		          .publishOn(Schedulers.parallel())
                .map(e -> {
                    System.out.println(Thread.currentThread().getName() + " map2 : " + e + "  " +
                            new Date());
                    return e + 1;
                })
                .subscribe(integer -> System.out.println(integer + " " + Thread.currentThread()));
        System.out.println(Thread.currentThread() + " return...");

    }

    public static void test2() {
        System.out.println("-- Mapping Flux elements --");
        Flux.just(1, 2, 3)
                .flatMap(integer -> {
                    System.out.println("-----------");
                    return Flux.range(integer, integer * 2)
                            .subscribeOn(Schedulers.newParallel("myThread", 8));
                }, 2)
                .subscribe(e -> System.out.println(e + " - " + Thread.currentThread().getName() + " - " + LocalTime.now()));
    }

    public static void test3() {
        System.out.println("-- Mapping Flux elements --");
        Flux.just(1, 2, 3)
                .flatMap(integer -> {
                    System.out.println("-----------");
                    return Flux.range(integer, integer * 2)
                            .subscribeOn(Schedulers.newParallel("myThread", 8));
                }, 1)
                .subscribe(e -> System.out.println(e + " - " + Thread.currentThread().getName() + " - " + LocalTime.now()));
    }
}
