package com.restaurantdelivery.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.dto.request.LoginRequest;
import com.restaurantdelivery.dto.request.RegisterUserRequest;
import com.restaurantdelivery.dto.request.ResetPasswordRequest;
import com.restaurantdelivery.dto.response.AuthResponse;
import com.restaurantdelivery.dto.response.UserResponse;
import com.restaurantdelivery.entity.User;
import com.restaurantdelivery.enums.UserStatus;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.exception.UnauthorizedException;
import com.restaurantdelivery.mapper.AuthMapper;
import com.restaurantdelivery.repository.UserRepository;
import com.restaurantdelivery.security.JwtUtil;

@Service
public class AuthService {

	Logger logger = LoggerFactory.getLogger(AuthService.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final AuthenticationManager authenticationManager;
	private final AuthMapper authMapper;
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
			AuthenticationManager authenticationManager, AuthMapper authMapper) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
		this.authMapper = authMapper;
	}
	
	
	public ResponseEntity<?> registerUser(RegisterUserRequest request) {
		logger.info("Registering an user");
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already registered");
		}
		
		User user = authMapper.toUserEntity(request, passwordEncoder.encode(request.getPassword()));
		user.setStatus(UserStatus.ACTIVE);
		UserResponse response = authMapper.toUserResponse(userRepository.save(user));
		logger.info("User Registered Successfully");
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	
	public ResponseEntity<?> login(LoginRequest request) {
		logger.info("User attempting to login");
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
		
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
		
		AuthResponse response = new AuthResponse(token, user.getEmail(), user.getRole().name());
		
		logger.info("User successfully logged in");
		return new ResponseEntity<>(response, HttpStatus.OK);
	
	}
	
	public ResponseEntity<?> forgotPassword(LoginRequest request) {
		
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		userRepository.save(user);
		
		return new ResponseEntity<>("New Password set successfully!", HttpStatus.OK);
	
	}
	
	public ResponseEntity<?> resetPassword(ResetPasswordRequest request){
		logger.info("User reseting password");
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
//            logger.warn("Password change failed: Incorrect current password for user ID: {}", userId);
            throw new UnauthorizedException("Current password is incorrect");
        }
		
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
//        logger.info("Password changed successfully for user ID: {}", userId);
        
        return ResponseEntity.ok("Password changed successfully");
	}
	
}
