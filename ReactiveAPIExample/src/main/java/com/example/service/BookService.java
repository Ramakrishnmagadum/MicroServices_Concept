package com.example.service;

import com.example.entities.BookEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
	public Mono<BookEntity> Create(BookEntity book);

	public Flux<BookEntity> GetAllBooks();

	public Mono<BookEntity> GetBookById(Integer bookId);

	public Mono<BookEntity> UpdateBookById(BookEntity book, int bookId);

	public Mono<Void> DeleteById(int bookId);

	public Flux<BookEntity> Search(String query);
}
