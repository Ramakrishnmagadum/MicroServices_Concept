package com.second.Controllers;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.second.model.Student;
import com.second.repo.StudentReactiveRepository;
import com.second.service.StudentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class StudentController {

	@Autowired
	StudentService studentService;
	

	//---- 1St way : By Using Annotation on top of the Method we are Routing the Request 
	@GetMapping(value = "/students", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Student> getStudent() {
		
		//-----Static Data we are returning Here -------
//		Flux<Student> flux = Flux
//				.just(new Student(1, "ram", 23), new Student(2, "shreeniddi", 23), new Student(3, "aditi", 23))
//				.delayElements(Duration.ofSeconds(1));
		//----From Database Emitting the Data------
		Flux<Student> flux2 = studentService.findAllStudents();
		flux2.subscribe(std -> System.out.println("student "+ std));

		Flux<Student> flux = flux2.delayElements(Duration.ofSeconds(1));
		return flux;
	}
	
	
	
	//2nd Way of Routing the Request ---------
	public Mono<ServerResponse> getStudents() {
		Flux<Student> flux = studentService.findAllStudents().delayElements(Duration.ofSeconds(1));
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(flux, Student.class);
	}
}
