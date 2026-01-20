package com.restaurantdelivery.mapper;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.RestaurantRequest;
import com.restaurantdelivery.dto.response.RestaurantResponse;
import com.restaurantdelivery.entity.Restaurant;

@Component
public class RestaurantMapper {

	public Restaurant toRestaurantEntity(RestaurantRequest request) {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(request.getName());
		restaurant.setDescription(request.getDescription());
		restaurant.setPhoneNumber(request.getPhoneNumber());
		restaurant.setEmail(request.getEmail());
		restaurant.setStreetAddress(request.getStreetAddress());
		restaurant.setCity(request.getCity());
		restaurant.setState(request.getState());
		restaurant.setPincode(request.getPincode());
		restaurant.setOpeningTime(request.getOpeningTime());
		restaurant.setClosingTime(request.getClosingTime());
		restaurant.setOpen(request.getOpen());
		
		return restaurant;
	}
	
	public RestaurantResponse toRestaurantResponse(Restaurant restaurant) {
		RestaurantResponse response = new RestaurantResponse();
		response.setId(restaurant.getId());
		response.setName(restaurant.getName());
		response.setDescription(restaurant.getDescription());
		response.setPhoneNumber(restaurant.getPhoneNumber());
		response.setEmail(restaurant.getEmail());
		response.setStreetAddress(restaurant.getStreetAddress());
		response.setCity(restaurant.getCity());
		response.setState(restaurant.getState());
		response.setPincode(restaurant.getPincode());
		response.setOpeningTime(restaurant.getOpeningTime());
		response.setClosingTime(restaurant.getClosingTime());
		response.setOpen(restaurant.getOpen());
		response.setAverageRating(restaurant.getAverageRating());
		response.setTotalReviews(restaurant.getTotalReviews());
		response.setCreatedAt(restaurant.getCreatedAt());
		response.setUpdatedAt(restaurant.getUpdatedAt());
		
		return response;
	}
	
	public void updateEntity(RestaurantRequest request,Restaurant restaurant ) {
		
		restaurant.setName(request.getName());
		restaurant.setDescription(request.getDescription());
		restaurant.setPhoneNumber(request.getPhoneNumber());
		restaurant.setEmail(request.getEmail());
		restaurant.setStreetAddress(request.getStreetAddress());
		restaurant.setCity(request.getCity());
		restaurant.setState(request.getState());
		restaurant.setPincode(request.getPincode());
		restaurant.setOpeningTime(request.getOpeningTime());
		restaurant.setClosingTime(request.getClosingTime());
		restaurant.setOpen(request.getOpen());
	}
	
}
