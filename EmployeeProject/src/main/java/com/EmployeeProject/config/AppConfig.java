package com.EmployeeProject.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
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
	
//	By Using RestTemaplateBuilder Object no need to create this RestTemplate Mannually ..
//	RestTemplateBuilder will create RestTemplate Object itself ....
//	@Bean
//	public RestTemplate getTemplate() {
//		return new RestTemplate();
//	}
	
	
//	This Will create WebClient Object and returns back...
	@Bean
	public WebClient getWebClient() {
		return WebClient.builder().baseUrl(addressBaseURL).build();

	}

}
