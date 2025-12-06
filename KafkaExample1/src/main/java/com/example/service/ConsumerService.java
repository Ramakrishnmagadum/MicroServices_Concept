package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
	
	@Autowired
	ConcurrentKafkaListenerContainerFactory<String, String> kafkaListener;
	
	@KafkaListener(topics = "test", groupId = "group1")
	public String reciveMsg(String msg) {
		System.out.println("Msg Recieved :"+msg);
		return "Msg Recieved :"+msg;
		
	}

}
