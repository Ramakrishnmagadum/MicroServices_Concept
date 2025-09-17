package com.EmployeeProject.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EmployeeProject.Entity.Employee;

public interface EmployeeDao  extends JpaRepository<Employee, Integer>{
	

}
