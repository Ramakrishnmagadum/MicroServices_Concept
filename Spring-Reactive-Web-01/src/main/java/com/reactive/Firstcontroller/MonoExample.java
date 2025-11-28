package com.reactive.Firstcontroller;

import java.time.Duration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

//Mono is a reactive type from Project Reactor used in Spring WebFlux.
////It represents a stream of 0 or 1 item — nothing more.
@RestController
public class MonoExample {

	@GetMapping("/name")
	public  Mono<String> getMonoMsg() throws InterruptedException {
	
		Mono<String> monoFlux = Mono.just("Ram").delayElement(Duration.ofSeconds(1));
	return monoFlux;
		
	}

}
