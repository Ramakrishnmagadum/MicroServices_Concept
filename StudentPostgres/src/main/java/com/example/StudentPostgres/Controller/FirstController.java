package com.example.StudentPostgres.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

	@GetMapping("/msg")
	public String getMsg() {
		return "Hello Good Morning Msg";
	}
}
