package com.example.Repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.BookEntity;

import reactor.core.publisher.Flux;

@Repository
public interface BookReactiveRepository extends ReactiveCrudRepository<BookEntity, Integer> {

	
	@Query("select * from xBook_Details where name = $1")
	public Flux<BookEntity> SearchByName(String name);
	
	
	@Query("select * from xBook_Details where name = $1 and book_Id = $2")
	public Flux<BookEntity> SearchByNameAndID(String name , Integer bookId);
}
