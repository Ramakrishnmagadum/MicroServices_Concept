package com.example.Mono;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public class ThenMethodExamples {

    public static void main(String[] args) throws InterruptedException {
        exampleThen();
        System.out.println("------------------------------------------------");
        exampleThenMono();
        System.out.println("------------------------------------------------");
        exampleThenReturn();
        System.out.println("------------------------------------------------");
        exampleThenMany();
        System.out.println("------------------------------------------------");
        exampleErrorPropagation();

        // Wait a little so async prints appear (only for demo purposes in main).
        Thread.sleep(500);
    }

    // 1) then() - upstream value dropped, result is Mono<Void>
    static void exampleThen() {
        Mono.just("hello")
            .doOnNext(s -> System.out.println("upstream produced: " + s))
            .then() // returns Mono<Void>
            .doOnSubscribe(s -> System.out.println("subscribed to then()"))
            .doOnSuccess(v -> System.out.println("then() completed (no value)"))
            .subscribe();
    }

    // 2) then(Mono<T>) - drop upstream value, then switch to supplied Mono
    static void exampleThenMono() {
        Mono.just("first")
            .doOnNext(s -> System.out.println("first: " + s))
            .then(Mono.fromCallable(() -> {
                System.out.println("creating second mono");
                return "second-value";
            }))
            .subscribe(v -> System.out.println("received: " + v));
    }

    // 3) thenReturn(...) - emit a constant after upstream completes
    static void exampleThenReturn() {
        Mono.just("ignored")
            .doOnNext(v -> System.out.println("upstream (ignored): " + v))
            .thenReturn("constant-after-upstream")
            .subscribe(v -> System.out.println("thenReturn emitted: " + v));
    }

    // 4) thenMany(Flux) - switch to a Flux after upstream completes
    static void exampleThenMany() {
        Mono.just("prepare")
            .doOnNext(v -> System.out.println("preparing: " + v))
            .thenMany(Flux.range(1, 3))
            .subscribe(i -> System.out.println("thenMany -> " + i));
    }

    // 5) error propagation — upstream error short-circuits the rest
    static void exampleErrorPropagation() {
        Mono.<String>error(new RuntimeException("upstream failed"))
            .then(Mono.just("won't run"))
            .doOnError(e -> System.out.println("caught error: " + e.getMessage()))
            .subscribe(
                v -> System.out.println("value: " + v),
                err -> {}, // handled via doOnError above
                () -> System.out.println("completed")
            );
    }
}

