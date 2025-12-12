package com.example.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GetInfo {

	@Autowired
	RestTemplate restTemplate;
	
	
	@GetMapping("msg1")
	public String getMsg1() {
		
		
		doRestCallToProject2();
		
		return "Hello Good-Morning";
	}


	private void doRestCallToProject2() {
		restTemplate.getForEntity("http://localhost:8083/msg2", String.class);
	}
}
