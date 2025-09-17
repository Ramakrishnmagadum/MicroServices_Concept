package com.AddressProject.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.AddressProject.Entity.Address;
import com.AddressProject.response.AddressReponse;
import com.AddressProject.service.AddressService;

@RestController
public class AddressController {
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	ModelMapper mapper;

	@GetMapping("/address/{employeeid}")
	public ResponseEntity<AddressReponse> getAddressByEmployeeId(@PathVariable("employeeid") int id) {
		Address address = addressService.getAddressByEmployeeId(id);
		AddressReponse response =mapper.map(address, AddressReponse.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
