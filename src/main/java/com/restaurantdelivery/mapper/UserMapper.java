package com.restaurantdelivery.mapper;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.UserRequest;
import com.restaurantdelivery.dto.response.UserResponse;
import com.restaurantdelivery.entity.User;

@Component
public class UserMapper {

	public User toUserEntity(UserRequest request) {
		User user = new User();
		user.setEmail(request.getEmail());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setPhoneNumber(request.getPhoneNumber());
		user.setStatus(request.getStatus());
		
		return user;
	}
	
	public UserResponse toUserResponse(User user) {
		
		UserResponse response = new UserResponse();
		response.setId(user.getId());
		response.setEmail(user.getEmail());
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setPhoneNumber(user.getPhoneNumber());
		response.setRole(user.getRole());
		response.setStatus(user.getStatus());
		response.setCreatedAt(user.getCreatedAt());
		response.setUpdatedAt(user.getUpdatedAt());
		
		return response;
	}
}
