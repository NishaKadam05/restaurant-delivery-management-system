package com.restaurantdelivery.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.config.CurrentUserUtil;
import com.restaurantdelivery.dto.request.DeliveryPartnerProfileRequest;
import com.restaurantdelivery.dto.response.DeliveryPartnerResponse;
import com.restaurantdelivery.entity.DeliveryPartnerProfile;
import com.restaurantdelivery.entity.User;
import com.restaurantdelivery.exception.BadRequestException;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.mapper.DeliveryPartnerMapper;
import com.restaurantdelivery.repository.DeliveryPartnerRepository;
import com.restaurantdelivery.repository.UserRepository;

@Service
public class DeliveryPartnerService {
	
	Logger logger = LoggerFactory.getLogger(DeliveryPartnerService.class);

	private final DeliveryPartnerRepository deliveryPartnerRepository;
	private final UserRepository userRepository;
	private final DeliveryPartnerMapper deliveryPartnerMapper;
	
	public DeliveryPartnerService(DeliveryPartnerRepository deliveryPartnerRepository, UserRepository userRepository,
			DeliveryPartnerMapper deliveryPartnerMapper) {
		super();
		this.deliveryPartnerRepository = deliveryPartnerRepository;
		this.userRepository = userRepository;
		this.deliveryPartnerMapper = deliveryPartnerMapper;
	}

	public ResponseEntity<?> createProfile(DeliveryPartnerProfileRequest request){
		
		logger.info("Creating delivery partner profile");
		
		User currentUser = getCurrentUser();
		
		if(deliveryPartnerRepository.findByUserId(currentUser.getId()).isPresent()) {
			throw new BadRequestException("Profile already exists");
		}
		
		if(deliveryPartnerRepository.existsByVehicleNumber(request.getVehicleNumber())) {
			throw new BadRequestException("Vehicle number already registered");
		}
		
		if(deliveryPartnerRepository.existsByLicenseNumber(request.getLicenseNumber())) {
			throw new BadRequestException("License number already registered");

		}
		
		DeliveryPartnerProfile profile = deliveryPartnerMapper.toEntity(request, currentUser);
		DeliveryPartnerResponse response = deliveryPartnerMapper.toResponse(deliveryPartnerRepository.save(profile));
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	public ResponseEntity<?> updateAvailability(Boolean available){
		
		logger.info("Updating availability status of delivery partner");
		
		DeliveryPartnerProfile profile = getCurrentProfile();
		profile.setAvailable(available);
		DeliveryPartnerResponse response = deliveryPartnerMapper.toResponse(deliveryPartnerRepository.save(profile));

		return ResponseEntity.ok(response);
		
	}
	
	public ResponseEntity<?> getAvailablePartners(){
		
		logger.info("Getting available partners");
		List<DeliveryPartnerProfile> profiles = deliveryPartnerRepository.findByAvailableTrue();
		
		if(profiles.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("No Available partners");
		}
		
		List<DeliveryPartnerResponse> profileList = new ArrayList<>();
		for(DeliveryPartnerProfile profile: profiles)
			profileList.add(deliveryPartnerMapper.toResponse(profile));
		
		return ResponseEntity.ok(profileList);
	}
	
	public ResponseEntity<?> myProfile(){
		DeliveryPartnerProfile profile = getCurrentProfile();
		DeliveryPartnerResponse response = deliveryPartnerMapper.toResponse(deliveryPartnerRepository.save(profile));
		return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<?> updateProfile(DeliveryPartnerProfileRequest request){
		
		logger.info("Updating delivery partner's profile");
		DeliveryPartnerProfile profile = getCurrentProfile();
		deliveryPartnerMapper.updateEntity(request,profile, profile.getUser());
		DeliveryPartnerResponse response = deliveryPartnerMapper.toResponse(deliveryPartnerRepository.save(profile));
		
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	private DeliveryPartnerProfile getCurrentProfile() {
		
		User user = getCurrentUser();
			return deliveryPartnerRepository.findByUserId(user.getId())
					.orElseThrow(() -> new ResourceNotFoundException("Profile Not Found"));
					
	}
	
	private User getCurrentUser() {
		
		return userRepository.findByEmail(CurrentUserUtil.getCurrentUserEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
	}
}
