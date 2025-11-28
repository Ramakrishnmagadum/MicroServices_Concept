package com.example.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.controller.BookController;

@Configuration
public class BookRouter2 {

	@Autowired
	BookController controller;
	
	@Bean
	public RouterFunction<ServerResponse> bookRouter() {
		 return RouterFunctions.route(RequestPredicates.POST("/book"),
				(request) -> { 
					return controller.CreateBook(request);
				});
	}
}
