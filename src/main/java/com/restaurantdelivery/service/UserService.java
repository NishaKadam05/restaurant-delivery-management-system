package com.restaurantdelivery.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.config.CurrentUserUtil;
import com.restaurantdelivery.dto.request.ResetPasswordRequest;
import com.restaurantdelivery.dto.response.UserResponse;
import com.restaurantdelivery.entity.User;
import com.restaurantdelivery.enums.UserRole;
import com.restaurantdelivery.enums.UserStatus;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.exception.UnauthorizedException;
import com.restaurantdelivery.mapper.UserMapper;
import com.restaurantdelivery.repository.UserRepository;

@Service
public class UserService {
	
	Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	public ResponseEntity<?> getAllUsers() {
		
		logger.info("Getting all user profiles");
		List<User> users = userRepository.findAll();

		if (users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No Users Registered");
		}

		List<UserResponse> userList = new ArrayList<>();
		for (User user : users)
			userList.add(userMapper.toUserResponse(user));

		return ResponseEntity.ok(userList);
	}

	public ResponseEntity<?> getUserById(Long id) {
		
		logger.info("Get user by id "+id);
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
		return ResponseEntity.ok(userMapper.toUserResponse(user));

	}

	public ResponseEntity<?> getUserByStatus(UserStatus status) {
		
		logger.info("Get user by status "+status);

		List<User> users = userRepository.findByStatus(status);

		if (users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No " + status + " Users");
		}

		List<UserResponse> userList = new ArrayList<>();
		for (User user : users)
			userList.add(userMapper.toUserResponse(user));

		return ResponseEntity.ok(userList);
	}

	public ResponseEntity<?> getUserByRole(UserRole role) {
		
		logger.info("Get user by role "+role);

		List<User> users = userRepository.findByRole(role);

		if (users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No Users with role " + role);
		}

		List<UserResponse> userList = new ArrayList<>();
		for (User user : users)
			userList.add(userMapper.toUserResponse(user));

		return ResponseEntity.ok(userList);
	}
	
	
	public ResponseEntity<?> getMyProfile(){
		User user = getCurrentUser();
		UserResponse response = userMapper.toUserResponse(user);
		
		return ResponseEntity.ok(response);
	}
	
	private User getCurrentUser() {
		return userRepository.findByEmail(CurrentUserUtil.getCurrentUserEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}

}
