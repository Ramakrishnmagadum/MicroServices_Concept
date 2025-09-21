package com.RESTAPIConectNegotiation.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.RESTAPIConectNegotiation.dto.Student;

@RestController
public class StudentController {

//	Notes:
//	1. Content-Type :-  It is a request header.It tells the server what type of data you are sending in the body of the request.
//	Example:Content-Type: application/json → means the body you are sending is in JSON format.
//		Content-Type: application/xml → body is in XML.
//		Content-Type: multipart/form-data → usually for file uploads.
//	2. Accept:- It is also a request header.It tells the server what kind of response format you want back.
//    Example:Accept: application/json → client expects JSON response.
//    Accept: application/xml → client expects XML response.
	
//	1. consumes:- Means → “What type of request body this API can accept.” It matches against the Content-Type header of the request.
//	2. produces:- Means → “What type of response format this API will return.” It matches against the Accept header of the request.

//	In application/json  and test/plain it will support in default 
//	But to support XML Need to add external Depedencies
//	<dependency>
//	    <groupId>com.fasterxml.jackson.dataformat</groupId>
//	    <artifactId>jackson-dataformat-xml</artifactId>
//  </dependency>
	
	
	//	Here produces:- Means In which Format We are returning the response.......
	@GetMapping(value="/student" , produces= {MediaType.TEXT_PLAIN_VALUE })
	public String getStudent1() {
		return new Student(1, "ram", "ram@gamail.com").toString();
	}
	

//	Here produces:- Means In which Format We are returning the response.......
	@GetMapping(value ="/student"  , produces= {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE })
	public Student getStudent2() {
		return new Student(1, "ram", "ram@gamail.com");
	}
	
//	Here produces:- Means In which Format We are returning the response.......
//	produces= {MediaType.TEXT_PLAIN_VALUE } ---this is optional here 
	@PostMapping(value="/student" , consumes= {MediaType.TEXT_PLAIN_VALUE } , produces= {MediaType.TEXT_PLAIN_VALUE })
	public String PostStudent(@RequestBody Student student) {
		System.out.println("PostStudent Student Executed ");
		return student.toString();
	}
	
//	Here produces:- Means In which Format We are returning the response.......
	@PostMapping(value="/student" , consumes= {MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE })
	public Student PostStuden2t(@RequestBody Student student) {
		System.out.println("PostStuden2 Student Executed ");
		return student;
	}
	

}
