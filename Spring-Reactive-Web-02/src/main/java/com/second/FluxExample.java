package com.second;

import reactor.core.publisher.Mono;

public class FluxExample {
public static void main(String[] args) {
	 Mono<String> monoPublisher = Mono.just("Good Morning");
	 monoPublisher.subscribe(data -> System.out.println("Data : "+data));
}
}
