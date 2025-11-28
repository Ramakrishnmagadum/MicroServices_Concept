package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Repositories.BookReactiveRepository;
import com.example.entities.BookEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	BookReactiveRepository repository;

	@Override
	public Mono<BookEntity> Create(BookEntity book) {
		System.out.println("Thread Name Before Create Action : " + Thread.currentThread().getName());
		Mono<BookEntity> entity = repository.save(book).doOnNext(
				data -> System.out.println("Thread Name Create Action Carried By " + Thread.currentThread().getName()));
		return entity;
	}

	@Override
	public Flux<BookEntity> GetAllBooks() {
		System.out.println("Thread Name Before GetAllBooks Action : " + Thread.currentThread().getName());
		Flux<BookEntity> entity = repository.findAll().doOnNext(data -> System.out
				.println("Thread Name GetAllBooks Action Carried By " + Thread.currentThread().getName()));
		return entity;
	}

	@Override
	public Mono<BookEntity> GetBookById(Integer bookId) {
		System.out.println("Thread Name Before GetBookById Action : " + Thread.currentThread().getName());
		Mono<BookEntity> entity = repository.findById(bookId).doOnNext(data -> System.out
				.println("Thread Name GetBookById Action Carried By " + Thread.currentThread().getName()));
		return entity;
	}

	@Override
	public Mono<BookEntity> UpdateBookById(BookEntity book, int bookId) {
		Mono<BookEntity> oldEntity = repository.findById(bookId);
		return oldEntity.flatMap(existingData -> {
			existingData.setAuthor(book.getAuthor());
			existingData.setDescription(book.getDescription());
			existingData.setName(book.getName());
			existingData.setPublisher(book.getPublisher());
			return repository.save(existingData);
		});

	}

	@Override
	public Mono<Void> DeleteById(int bookId) {
		Mono<Void> entity = repository.deleteById(bookId);
		return entity;
	}

	@Override
	public Flux<BookEntity> Search(String name) {
		return repository.SearchByName(name);
	}
	
	public Flux<BookEntity> SearchByNameAndID(String name, Integer bookId) {
		return repository.SearchByNameAndID(name, bookId);
	}
}