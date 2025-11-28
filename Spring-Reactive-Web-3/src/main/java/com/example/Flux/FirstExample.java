package com.example.Flux;

import java.util.Arrays;
import java.util.List;

import reactor.core.publisher.Flux;

public class FirstExample {
	public static void main(String[] args) {
//we can create FLux By 2 ways
//		1 . By Using Just() Method
		Flux.just("Ram","prashant").subscribe(name -> System.out.println("Name "+name));
		
//		2. By Using fromIterable() 
		List<String> listOfNames = Arrays.asList("Ram","prashant","Hello");
		Flux.fromIterable(listOfNames).subscribe(names -> System.out.println(names));
		
		
		//Example Using Map() Method
		Flux.just("one","Two","Three","Four","Five","Sixth").map(num -> num.toUpperCase()).subscribe(num -> System.out.print(num+" "));
		System.out.println();
		
		//Example Using Filter() Method
		Flux.just("one","Two","Three","Four","Five","Sixth").filter(num -> num.length()>4).subscribe(num -> System.out.println(num));
	}
}
