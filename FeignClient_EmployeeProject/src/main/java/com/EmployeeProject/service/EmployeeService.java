package com.EmployeeProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EmployeeProject.Dao.EmployeeDao;
import com.EmployeeProject.Entity.Employee;

@Service
public class EmployeeService {

	@Autowired
	EmployeeDao employeeDao;

	public Employee getEmployee(int id) {
		Employee employee = employeeDao.findById(id).get();
		return employee;
	}
}
