package com.ConfigServerService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

//What this annotation will do @EnableConfigServer
//"This application is not just a normal Spring Boot app, it should act as a Config Server that serves configuration to other microservices."
//What happens when you add @EnableConfigServer?
//1. Turns your Spring Boot app into a Config Server
//It activates Spring Cloud Config Server auto-configuration.
//Your application starts listening for configuration requests from clients.
//
//2. Exposes REST endpoints for config clients.
//
//3. Loads configuration from a backend store
//(depending on how you configure it in application.yml):
//Git (most common) , File system , Vault , JDBC (database) so on
@EnableConfigServer

@SpringBootApplication
public class ConfigServerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerServiceApplication.class, args);
	}

}
