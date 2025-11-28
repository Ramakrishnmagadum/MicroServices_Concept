package com.example.Mono;

import org.reactivestreams.Subscription;

import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

public class FirstExample {
	public static void main(String[] args) {
		Mono<String> monoPublisher = Mono.just("ramakrishna");
		monoPublisher.subscribe(new CoreSubscriber() {

			@Override
			public void onNext(Object data) {
				System.out.println("Data : "+data);
			}

			@Override
			public void onError(Throwable t) {
				System.out.println("Error : "+ t.getMessage());
			}

			@Override
			public void onComplete() {
				System.out.println("Your Task completed");
			}

			@Override
			public void onSubscribe(Subscription s) {
				System.out.println("Subcription successfully done");
				s.request(2);
			}
		});
	}
}
