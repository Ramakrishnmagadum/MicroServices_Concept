package com.second.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.second.model.Student;

public interface StudentReactiveRepository extends ReactiveCrudRepository<Student, Integer>{

}
