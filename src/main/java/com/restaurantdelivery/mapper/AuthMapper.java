package com.restaurantdelivery.mapper;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.RegisterUserRequest;
import com.restaurantdelivery.dto.response.UserResponse;
import com.restaurantdelivery.entity.User;
import com.restaurantdelivery.enums.UserRole;
import com.restaurantdelivery.enums.UserStatus;

@Component
public class AuthMapper {

	public User toUserEntity(RegisterUserRequest request, String encodedPassword) {
		
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(encodedPassword);
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setPhoneNumber(request.getPhoneNumber());
		user.setRole(request.getRole());
		
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
