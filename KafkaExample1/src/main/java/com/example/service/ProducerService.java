package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

@Service
public class ProducerService {
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "test";

    public String sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
        return "Message sent: " + message;
    }
}
