package com.EmployeeProject.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.EmployeeProject.Entity.Employee;
import com.EmployeeProject.FeignClient.AddressProjectFeignClient;
import com.EmployeeProject.response.AddressReponse;
import com.EmployeeProject.response.EmployeeResponse;
import com.EmployeeProject.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	AddressProjectFeignClient addressProjectFeignClient;

	@GetMapping("/employee/{id}") 	
	public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable("id") int id) {
		Employee employee = employeeService.getEmployee(id);

		EmployeeResponse reponse = mapper.map(employee, EmployeeResponse.class);

//		Address Response should take response from AddressProject ...(By Using FeignClient)
//		Here We are using FeignClient To make RestCall -->
		AddressReponse addressReponse = addressProjectFeignClient.getAddressResponse(id);
		reponse.setAddressReponse(addressReponse);

        return ResponseEntity.status(HttpStatus.OK).body(reponse);
	}

}
