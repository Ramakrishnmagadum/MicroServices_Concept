package com.EmployeeProject.FeignClient;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.EmployeeProject.response.AddressReponse;


//this annotation will helps to create Proxy class for this interface and provides implementations for this URL and EndPoints ....

@FeignClient(name="addressProject", path="/address-app/api") //For ScaleUp : - > If Multiple Servers are started (of the same project ) then BaseURl also multiple(different diffrenet Port Numbers) ,that's Why removing the URL form here and By the Help Of LoadBalanacer we will pass dynamic URL 
//LoadBalanacer are Many are thier 
//1. Ribbon Load Balanacer :- Here In application.propeties need to mention the Multiple URL's ..for multiple Servers
//In Sequence wise this will send request to different different Servers ....
@RibbonClient(name="addressProject")  //addressProject.ribbon.ListOfServers = URL need to mention (In Application.properties file)
public interface AddressProjectFeignClient {

//	If am calling getAddressResponse() Method ---> Then it will build the Rest Call with Above mentioned URL and add this "/address/{employeeid}" EndPoints also 
//	and Make call and returns the Response ....
	@GetMapping("/address/{employeeid}")
	public AddressReponse getAddressResponse(@PathVariable("employeeid") int id);
}
