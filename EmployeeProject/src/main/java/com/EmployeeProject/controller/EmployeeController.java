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
import com.EmployeeProject.response.AddressReponse;
import com.EmployeeProject.response.EmployeeResponse;
import com.EmployeeProject.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	ModelMapper mapper;
	
//	@Autowired
//	RestTemplate restTemplate;
	
	@Autowired
	private WebClient webClient;
	
	
//	This URL will take Dynamic value come  from application.properties for this key....
//	@Value("${addressservice.base.url}")
//	private String addressBaseURL;
	
	
//Here Using ResttemplateBuilder Class   ---> to Build the RestTemplate Object ---> It will create RestTemplateObject and we can customize this Object also 
//	Example : Setting RootUri For this RestTemplate ...
//	@Value("${addressservice.base.url}") String addressBaseURL writing like this -----> BCZ This Value should present while constructer Called ...
//	public EmployeeController(@Value("${addressservice.base.url}") String addressBaseURL , RestTemplateBuilder builder) {
//		 this.restTemplate = builder
//		.rootUri(addressBaseURL)
//		.build();
//		
//		
//	}

	@GetMapping("/employee")
	@ResponseBody
	public String getEmployee() {
	return "employee dummy Page";
	}

	@GetMapping("/employee/{id}")
//	We should not return Employee Object ..this is not good Practice... so for that we will create one Response Object for EMployee and add the data to that object and will return...
	public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable("id") int id) {
		Employee employee = employeeService.getEmployee(id);
		
//		#   -----> From Employee to EmployeeResponse we are adding data manually
//		EmployeeResponse response=new EmployeeResponse();
//		reponse.setId(employee.getId());
//		reponse.setBloodgroup(employee.getBloodgroup());
//		reponse.setEmail(employee.getEmail());
//		reponse.setName(employee.getName());
		
		
//		#----> Here By ModelMapper (this is easiest to map the data) ... we are mapping the data from Employee to--> EmployeeResponse...
//		Steps to do it :- 1. Add ModelMapper Dependencies  2.Create ModelMapper Object   3. User ModelMapper Object Here to Map...
		EmployeeResponse reponse = mapper.map(employee, EmployeeResponse.class);
		
//		AddressReponse addressReponse = restTemplate.getForObject(addressBaseURL+"/address/{id}", AddressReponse.class, 1);  //## Here Setting RootUri 
//		#with RestTemplate Call
//		AddressReponse addressReponse = restTemplate.getForObject("/address/{id}", AddressReponse.class, 1);//## In this Case RootURI already Setted so No Need to set it Externally ....
		
		
//		## This will build rest call and retrive the response...
		AddressReponse addressReponse = webClient.get().uri("/address/"+id)
		.retrieve()
		.bodyToMono(AddressReponse.class)
		.block();
		reponse.setAddressReponse(addressReponse);
		
//		We are returning ResponseEntity with Body "EMployeeResoponse ....
//		Why we are returning ResponseEntity  --> by this we can add additional Info  to that .....so it will helpful..
		return ResponseEntity.status(HttpStatus.OK).body(reponse);
	}
	
	
}
