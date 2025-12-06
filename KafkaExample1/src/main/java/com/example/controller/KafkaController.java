package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.ProducerService;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
	
	 @Autowired
	    private ProducerService producerService;
	 
	    @GetMapping("/send/{message}")
	    public String sendMessage(@PathVariable String message) {
	        return producerService.sendMessage(message);
	    }
}
