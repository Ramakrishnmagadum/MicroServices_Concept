package com.HotelService.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HotelService.Entities.Hotel;
import com.HotelService.Exception.ResourceNotFoundException;
import com.HotelService.Repository.HotelRepository;

@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	HotelRepository hotelRepository;

	@Override
	public Hotel create(Hotel hotel) {
		String uuid=UUID.randomUUID().toString();
		hotel.setId(uuid);
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		return hotelRepository.findAll();
	}

	@Override
	public Hotel get(String id) {
		return hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Hotel not found for this ID"+ id));
	}

}
