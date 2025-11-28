package com.reactive.Firstcontroller;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;

public class FluxExample {
	public static void main(String[] args) throws InterruptedException {
		List<String> stringList = Arrays.asList("ram", "krishna", "prashant", "Aditi", "ShreeNiddi");

		Flux<String> publisher = Flux.fromIterable(stringList) .delayElements(Duration.ofSeconds(1));

		publisher.subscribe(str -> System.out.println("Printing the Value " + str));
		Thread.sleep(10000);
	}
}
