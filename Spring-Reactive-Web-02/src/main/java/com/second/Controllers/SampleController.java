package com.second.Controllers;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class SampleController {

	@GetMapping(value = "/msg", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getMsg() {
		Flux<String> flux = Flux.just("Ram", "prashant", "Aditi", "Shreeniddi").delayElements(Duration.ofSeconds(1));
		return flux;
	}

	
}
