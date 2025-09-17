package com.AddressProject.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Appconfig {
	
	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();
	}

}
