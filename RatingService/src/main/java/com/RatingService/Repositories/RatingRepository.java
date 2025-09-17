package com.RatingService.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.RatingService.Entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, String> {

	//Custom Finder Methods..
	List<Rating> findByUserId(String userId); 
	List<Rating> findByHotelId(String HotelId); 
}
