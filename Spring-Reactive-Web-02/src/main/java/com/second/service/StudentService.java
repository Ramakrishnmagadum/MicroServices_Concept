package com.second.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.second.model.Student;
import com.second.repo.StudentReactiveRepository;

import reactor.core.publisher.Flux;

@Service
public class StudentService {

	@Autowired
	StudentReactiveRepository studentRepo;

	public Flux<Student> findAllStudents() {
		return studentRepo.findAll();
	}
}
