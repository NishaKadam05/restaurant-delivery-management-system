package com.restaurantdelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantdelivery.service.PickUpService;

@RestController
@RequestMapping("/pickup")
@PreAuthorize("hasRole('CUSTOMER')")
public class PickUpController {

	@Autowired
	private PickUpService pickUpService;
	
	@PatchMapping("/{orderId}")
	public ResponseEntity<?> markPickedUp(@PathVariable Long orderId){
		return pickUpService.markPickedUp(orderId);
	}
}
