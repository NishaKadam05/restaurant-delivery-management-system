package com.restaurantdelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantdelivery.dto.request.ReviewRequest;
import com.restaurantdelivery.enums.ReviewType;
import com.restaurantdelivery.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;

	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping
	public ResponseEntity<?> createReview(@Valid @RequestBody ReviewRequest request){
		return reviewService.createReview(request);
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/myReviews")
	public ResponseEntity<?> currentUserReviews(){
		return reviewService.myReviews();
	}
	
	@GetMapping
	public ResponseEntity<?> getAllReviews(){
		return reviewService.getAllReviews();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getReviewById(@PathVariable Long id){
		return reviewService.getReviewById(id);
	}
	
	@GetMapping("/type/{reviewType}")
	public ResponseEntity<?> getReviewByReviewType(@PathVariable ReviewType type){
		return reviewService.getReviewsByReviewType(type);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{customerId}")
	public ResponseEntity<?> getReviewsByCustomerId(@PathVariable Long id){
		return reviewService.getReviewsByCustomerId(id);
		
	}
}
