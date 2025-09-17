package com.First_Employee_Layer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class EmployeeController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/employee")
	public String getEmployeeDetails() {
		String customerDetails= restTemplate.getForObject("http://localhost:8080/customer", String.class);
		return "I am AuraCloud Private Limited Employee.... : "+customerDetails;
	}

}