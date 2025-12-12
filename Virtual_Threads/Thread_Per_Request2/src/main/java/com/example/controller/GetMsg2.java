package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetMsg2 {

	@GetMapping("msg2")
	public String getMsg2() {
		return "Good Evening";
	}
}
