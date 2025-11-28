package com.reactive.Firstcontroller;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

//With Flux --> It won't block 
//If first value is ready then it will print and then it will do other work if it has
//Then second value is ready ---> it will take second value and print it then it will do other work
//Means it will get notify if data are ready it will print and again it will continue it's work ---> so its called Non-Blocking Call...Using Flux
@RestController
public class Non_Blocking_With_Flux {
	
	@GetMapping("/names")
	public Flux<String> getMsg() throws InterruptedException{
		List<String> stringList= Arrays.asList("ram","krishna","prashant","Aditi","ShreeNiddi");
		
		Flux<String> publisher = Flux.fromIterable(stringList).delayElements(Duration.ofSeconds(1));
		return publisher;
		
	}
}
