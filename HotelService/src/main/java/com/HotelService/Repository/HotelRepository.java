package com.HotelService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HotelService.Entities.Hotel;

public interface HotelRepository  extends JpaRepository<Hotel, String>{

}
