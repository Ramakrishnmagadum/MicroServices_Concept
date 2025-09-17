package com.RatingService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RatingService.Entities.Rating;
import com.RatingService.Service.RatingServiceImpl;

@RestController
@RequestMapping("/ratings")
public class RatingController {

	@Value("${app.message}")
	private String message;
	
	@Autowired
	RatingServiceImpl ratingServiceImpl;

	// create rating
//	@RequestBody --How it works 
//Incoming request: When a client sends data in the body of an HTTP request (for example, JSON in a POST or PUT request), Spring MVC reads that body.
//Deserialization: Spring uses an HttpMessageConverter (like Jackson for JSON) to convert that body into the corresponding Java object.
//Binding to method parameter: The converted object is assigned to the method parameter annotated with @RequestBody.
	@PostMapping
	public ResponseEntity<Rating> create(@RequestBody Rating rating) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingServiceImpl.create(rating));
	}

	// get all
	@GetMapping
	public ResponseEntity<List<Rating>> getRating() {
		return ResponseEntity.status(HttpStatus.OK).body(ratingServiceImpl.getRatings());
	}

	// get Rating By UserId
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<Rating>> getRatingUserId(@PathVariable("userId") String userId) {
		return ResponseEntity.status(HttpStatus.OK).body(ratingServiceImpl.getRatingsByUserId(userId));
	}

	// get Rating By HotelId
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<List<Rating>> getRatingHotelId(@PathVariable("hotelId") String hotelId) {
		return ResponseEntity.status(HttpStatus.OK).body(ratingServiceImpl.getRatingByHotelId(hotelId));
	}
	
	
	@GetMapping("/message")
	public String getMessage() {
		return message;
	}
}
