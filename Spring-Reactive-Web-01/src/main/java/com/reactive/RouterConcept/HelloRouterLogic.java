package com.reactive.RouterConcept;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class HelloRouterLogic {

	public Mono<ServerResponse> helloController(ServerRequest request) {
		String name = request.pathVariable("yourname");
		Mono<String> mono = Mono.just("Your name is " + name);
		return ServerResponse.ok().body(mono, String.class);
	}

	public Mono<ServerResponse> goodMorning(ServerRequest request) {
		String name = request.pathVariable("yourname");
		Mono<String> mono = Mono.just("Good Morning " + name);
		return ServerResponse.ok().body(mono, String.class);
	}
}
