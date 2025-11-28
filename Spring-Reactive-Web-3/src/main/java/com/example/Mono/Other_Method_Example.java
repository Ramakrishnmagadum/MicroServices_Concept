package com.example.Mono;

import java.time.Duration;

import reactor.core.publisher.Mono;

public class Other_Method_Example {
	public static void main(String[] args) throws InterruptedException {
		Mono<String> mono = Mono.just("Ramakrisha");
		mono.map(name -> name.toUpperCase())
		.subscribe(name -> System.out.println("Name in Capital Letters "+ name));
		
		
		//ConcatWith() Method
		Mono<String> mono2 = Mono.just(" Magadum");
		mono.concatWith(mono2).subscribe(fullname -> System.out.print(fullname+" "));
		
		//Delayelements() Method
		Mono<String> mono3 = Mono.just("Waiting for 2 seconds");
		mono3.delayElement(Duration.ofSeconds(2)).subscribe(msg -> System.out.println("\n Msg "+ msg));
		
		//Zip() Method 
//		How Does Zip Work Internally?
//				Waits until all Monos publish a value.
//				Combines them using tuple or function.
//				Emits one combined item.
//				Completes.
//				If any Mono errors → zip errors immediately.
				
		Mono<String> mono4 = Mono.just("A");
		Mono<Integer> mono5 = Mono.just(100);

		Mono.zip(mono4, mono5)
		    .subscribe(tuple -> {
		    	System.out.println("\nMono Zip Method executing");
		        String s = tuple.getT1();
		        Integer i = tuple.getT2();
		        System.out.println(s + " - " + i);
		    });
		
		
		//mono.zipWith(otherMono)
		Mono<String> mono6 = Mono.just("Hello");
		Mono<String> mono7 = Mono.just("World");
		mono6.zipWith(mono7)
		     .subscribe(tuple -> 
		     {
		    	  System.out.println("\nMono ZipWith  Method executing");
		         System.out.println(tuple.getT1() + " " + tuple.getT2());
		     }
		     );
		
		//Difference Berween Zip() and ZipWith() Method 
//		| Feature  | `zip()`               | `zipWith()`       |
//		| -------- | --------------------- | ----------------- |
//		| How used | Static method         | Instance method   |
//		| Inputs   | Multiple Monos        | One other Mono    |
//		| Output   | Tuple (T1, T2, T3...) | Tuple (T1, T2)    |
//		| Use case | Combine many streams  | Combine 2 streams |

		
		Thread.sleep(5000);
	}
}
