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

import com.restaurantdelivery.dto.request.ResetPasswordRequest;
import com.restaurantdelivery.enums.UserRole;
import com.restaurantdelivery.enums.UserStatus;
import com.restaurantdelivery.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<?> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id){
		return userService.getUserById(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/status/{status}")
	public ResponseEntity<?> getUserByStatus(@PathVariable UserStatus status){
		return userService.getUserByStatus(status);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/role/{role}")
	public ResponseEntity<?> getUserByRole(@PathVariable UserRole role){
		return userService.getUserByRole(role);
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER','DELIVERY_PARTNER')")
	@GetMapping("/profile")
	public ResponseEntity<?> getMyProfile(){
		return userService.getMyProfile();
	}
	
}
