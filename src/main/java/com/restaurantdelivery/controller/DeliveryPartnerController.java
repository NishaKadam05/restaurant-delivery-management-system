package com.restaurantdelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantdelivery.dto.request.DeliveryPartnerProfileRequest;
import com.restaurantdelivery.service.DeliveryPartnerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/delivery-partner-profile")
public class DeliveryPartnerController {

	@Autowired
	private DeliveryPartnerService deliveryPartnerService;
	
	@PreAuthorize("hasRole('DELIVERY_PARTNER')")
	@PostMapping
	public ResponseEntity<?> createProfile(@Valid @RequestBody DeliveryPartnerProfileRequest request){
		return deliveryPartnerService.createProfile(request);
	}
	
	@PreAuthorize("hasRole('DELIVERY_PARTNER')")
	@PatchMapping("/updateAvailability/{available}")
	public ResponseEntity<?> updateAvailability(@PathVariable Boolean available){
		return deliveryPartnerService.updateAvailability(available);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_STAFF')")
	@GetMapping("/availablePartners")
	public ResponseEntity<?> getAvailablePartners(){
		return deliveryPartnerService.getAvailablePartners();
	}
	
	@PreAuthorize("hasRole('DELIVERY_PARTNER')")
	@GetMapping("/myProfile")
	public ResponseEntity<?> myProfile(){
		return deliveryPartnerService.myProfile();
	}
	
	@PreAuthorize("hasRole('DELIVERY_PARTNER')")
	@PutMapping("/updateProfile")
	public ResponseEntity<?> updateProfile(@RequestBody DeliveryPartnerProfileRequest request){
		return deliveryPartnerService.updateProfile(request);
	}

}
