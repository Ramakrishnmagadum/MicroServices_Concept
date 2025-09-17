package com.First_Customer_Layer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
	
	@GetMapping("/customer")
	public String getCustomerDetails() {
		return "Hello Myself Ramakrishna";
	}

}
