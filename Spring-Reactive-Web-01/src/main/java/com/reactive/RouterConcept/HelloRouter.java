package com.reactive.RouterConcept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class HelloRouter {

	@Autowired
	HelloRouterLogic helloRouterLogic;

	@Bean
	RouterFunction<ServerResponse> routerConfig() {

		// -----------  This Code will Represent How The WebFlux will works/uses Internally --------------
//		RequestPredicate request = RequestPredicates.GET("/hello");
//		HandlerFunction<ServerResponse> handler = new HandlerFunction<ServerResponse>() {
//			@Override
//			public Mono<ServerResponse> handle(ServerRequest request) {
//				Flux<String> names = Flux.just("rama", "prashant", "aditi", "shreeniddi");
//				Mono<ServerResponse> serverResponse = ServerResponse.ok().body(names, String.class);
//				return serverResponse;
//			}
//		};
//		RouterFunction<ServerResponse> returnMsg =  RouterFunctions.route(request, handler);

		
//		----------Same Code -->But Optimized------------------
//		RouterFunction<ServerResponse> returnMsg =  RouterFunctions.route(RequestPredicates.GET("/hello"), ((request) ->
//			{ Flux<String> names = Flux.just("rama", "prashant", "aditi", "shreeniddi");
//			Mono<ServerResponse> serverResponse = ServerResponse.ok().body(names, String.class);  //here we can add Content-Type As well
//			return serverResponse;
//		}));

////    --------Creating separate Class So we can write Logic In that------------
//		RouterFunction<ServerResponse> returnMsg = RouterFunctions.route(RequestPredicates.GET("/hello/{yourname}"), (request) -> {  return helloRouterLogic.helloController(request); });
//		return returnMsg;
		
//		-----------we can Add Multiple Routes with same RouterFunctions....--------------- 
		RouterFunction<ServerResponse> returnMsg = RouterFunctions.route(RequestPredicates.GET("/hello/{yourname}"), (request) -> {  return helloRouterLogic.helloController(request); })
				.andRoute(RequestPredicates.GET("/hi/{yourname}"), (request) -> {  return helloRouterLogic.helloController(request); })
				.andRoute(RequestPredicates.GET("/goodMorning/{yourname}"), (request) -> {  return helloRouterLogic.goodMorning(request); });
		return returnMsg;

	}
}
