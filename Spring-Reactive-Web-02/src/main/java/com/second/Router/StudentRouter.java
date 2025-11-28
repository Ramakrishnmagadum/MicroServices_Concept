package com.second.Router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.second.Controllers.StudentController;

@Configuration
public class StudentRouter {

	@Autowired
	StudentController controller;

	@Bean
	RouterFunction<ServerResponse> routerConfig() {
		RouterFunction<ServerResponse> returnMsg = RouterFunctions.route(RequestPredicates.GET("/studentList"),
				(request) -> {
					return controller.getStudents();
				});
		return returnMsg;
	}
}
