package com.restaurantdelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantdelivery.dto.request.LoginRequest;
import com.restaurantdelivery.dto.request.RegisterUserRequest;
import com.restaurantdelivery.dto.request.ResetPasswordRequest;
import com.restaurantdelivery.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequest request){
		return authService.registerUser(request);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
		return authService.login(request);
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@Valid @RequestBody LoginRequest request){
		return authService.forgotPassword(request);
	}
	
	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request){
		return authService.resetPassword(request);
	}
	
}
