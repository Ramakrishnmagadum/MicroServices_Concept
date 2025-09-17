package com.AddressProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AddressProject.Dao.AddressDao;
import com.AddressProject.Entity.Address;

@Service
public class AddressService {
	@Autowired
	AddressDao addressDao;
	
	public Address getAddressByEmployeeId(int employee_id) {
		Address address=addressDao.findAddressByEmployeeId(employee_id);
		System.out.println("address running on here");
		return address;
	}

}
