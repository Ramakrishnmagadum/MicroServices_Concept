package com.EmployeeProject.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
	
	@Value("${addressservice.base.url}")
	private String addressBaseURL;
	
	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();
	}
}
