package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.entities.BookEntity;
import com.example.service.BookServiceImpl;

import reactor.core.publisher.Mono;

@Component
public class BookController {

	@Autowired
	BookServiceImpl service;

	
	public Mono<ServerResponse> CreateBook(ServerRequest request) {
		BookEntity entity = new BookEntity();
		entity.setName("BhagavtGetta");
		entity.setDescription("Good For Good");
		entity.setPublisher("Me");
		entity.setAuthor("Ved Vyasa");
		Mono<BookEntity> bookEntity = service.Create(entity);

		return ServerResponse.ok().body(bookEntity, BookEntity.class);
	}
}
