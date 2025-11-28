package com.example.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "xBook_Details")
public class BookEntity {
	@Id
	@Column(value="book_id")
	private Integer bookId;
	private String name;
	private String Description;
	private String publisher;
	private String author;

	public BookEntity() {
		super();
	}
	public BookEntity(Integer bookId, String name, String description, String publisher, String author) {
		super();
		this.bookId = bookId;
		this.name = name;
		Description = description;
		this.publisher = publisher;
		this.author = author;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
