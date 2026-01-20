package com.restaurantdelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantdelivery.dto.request.RestaurantRequest;
import com.restaurantdelivery.service.RestaurantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

	@Autowired 
	private RestaurantService restaurantService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> createRestaurant(@Valid @RequestBody RestaurantRequest request){
		return restaurantService.createRestaurant(request);
	}
	
	@GetMapping
	public ResponseEntity<?> getRestaurant(){
		return restaurantService.getRestaurant();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateRestaurant(@Valid @RequestBody RestaurantRequest request, @PathVariable Long id){
		return restaurantService.updateRestaurant(request, id);
	}
}
