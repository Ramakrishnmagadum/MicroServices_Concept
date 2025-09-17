package com.RatingService.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RatingService.Entities.Rating;
import com.RatingService.Repositories.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService{

	@Autowired
	RatingRepository ratingRepository;
	
	@Override
	public Rating create(Rating rating) {
		String uuid =UUID.randomUUID().toString();
		rating.setRatingId(uuid);
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getRatings() {
		return ratingRepository.findAll();
	}

	@Override
	public List<Rating> getRatingsByUserId(String userId) {
		return ratingRepository.findByUserId(userId);
	}

	@Override
	public List<Rating> getRatingByHotelId(String hotelId) {
		return ratingRepository.findByHotelId(hotelId);
	}

}
