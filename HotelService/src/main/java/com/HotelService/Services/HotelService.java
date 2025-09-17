package com.HotelService.Services;

import java.util.List;

import com.HotelService.Entities.Hotel;

public interface HotelService {

	
	//create
	Hotel create(Hotel hotel); 
	
	//getal
	List<Hotel> getAll();
	
	//get single 
	Hotel get(String id);
	
}
