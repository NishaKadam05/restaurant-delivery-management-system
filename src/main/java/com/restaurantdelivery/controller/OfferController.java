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

import com.restaurantdelivery.dto.request.OfferRequest;
import com.restaurantdelivery.service.OfferService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

	@Autowired
	private OfferService offerService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<?> createOffer(@Valid @RequestBody OfferRequest request){
		return offerService.createOffer(request);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateOffer(@Valid @RequestBody OfferRequest request,@PathVariable Long id){
		return offerService.updateOffer(request, id);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllOffers(){
		return offerService.getAllOffers();
	}
	
	@GetMapping("/active")
	public ResponseEntity<?> getActiveOffers(){
		return offerService.getActiveOffers();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/deactivate/{id}")
	public ResponseEntity<?> deactivateOffer(@PathVariable Long id){
		return offerService.deactivateOffer(id);
	}
	
}
