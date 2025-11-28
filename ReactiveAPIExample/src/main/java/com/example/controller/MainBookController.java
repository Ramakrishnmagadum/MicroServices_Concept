package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.BookEntity;
import com.example.service.BookServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MainBookController {

	@Autowired
	BookServiceImpl service;

	@PostMapping("/books")
	public Mono<BookEntity> CreateBook(@RequestBody BookEntity bookEntity) {
		return service.Create(bookEntity);
	}

	@GetMapping("/books")
	public Flux<BookEntity> GetAllBooks() {
		return service.GetAllBooks();
	}

	@GetMapping("/books/{bookId}")
	public Mono<BookEntity> GetBookById(@PathVariable Integer bookId) {
		return service.GetBookById(bookId);
	}

	@DeleteMapping("/books/{bookId}")
	public Mono<Void> DeleteById(@PathVariable Integer bookId) {
		return service.DeleteById(bookId);
	}

	@PutMapping("/books/{bookId}")
	public Mono<BookEntity> UpdateById(@RequestBody BookEntity entity, @PathVariable Integer bookId) {
		return service.UpdateBookById(entity, bookId);
	}

	@GetMapping("/bookByName/{name}")
	public Flux<BookEntity> SearchByName(@PathVariable String name) {
		return service.Search(name);
	}
	
	@GetMapping("/bookByName/{name}/{bookId}")
	public Flux<BookEntity> SearchByNameAndID(@PathVariable String name ,@PathVariable Integer bookId) {
		return service.SearchByNameAndID(name, bookId);
	}

}
