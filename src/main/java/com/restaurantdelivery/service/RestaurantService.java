package com.restaurantdelivery.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.dto.request.RestaurantRequest;
import com.restaurantdelivery.dto.response.RestaurantResponse;
import com.restaurantdelivery.entity.Restaurant;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.mapper.RestaurantMapper;
import com.restaurantdelivery.repository.RestaurantRepository;


@Service
public class RestaurantService {
	
	Logger logger = LoggerFactory.getLogger(RestaurantService.class);
	
	private final RestaurantRepository restaurantRepository;
	private final RestaurantMapper restaurantMapper;
	
	public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper) {
		super();
		this.restaurantRepository = restaurantRepository;
		this.restaurantMapper = restaurantMapper;
	}
	
	public ResponseEntity<?> createRestaurant(RestaurantRequest request){
		
		Restaurant restaurant = restaurantMapper.toRestaurantEntity(request);
		Restaurant savedRestaurant = restaurantRepository.save(restaurant);
		RestaurantResponse response = restaurantMapper.toRestaurantResponse(savedRestaurant);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	} 
	
	public ResponseEntity<?> getRestaurant(){
		
		logger.info("Getting restaurant details");
		List<Restaurant> restaurants = restaurantRepository.findAll();
		if(restaurants.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("No Restaurant Details");
		}
		
		List<RestaurantResponse> restaurantList = new ArrayList<>();
		for(Restaurant restaurant: restaurants) {
			restaurantList.add(restaurantMapper.toRestaurantResponse(restaurant));
		}
		return ResponseEntity.ok(restaurantList);
	}
	
	public ResponseEntity<?> updateRestaurant(RestaurantRequest request, Long id){
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));
		
		restaurantMapper.updateEntity(request, restaurant);
		RestaurantResponse response = restaurantMapper.toRestaurantResponse(restaurantRepository.save(restaurant));
		
		return ResponseEntity.ok(response);
	}
	
}
