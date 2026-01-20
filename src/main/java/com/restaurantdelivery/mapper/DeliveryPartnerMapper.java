package com.restaurantdelivery.mapper;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.DeliveryPartnerProfileRequest;
import com.restaurantdelivery.dto.response.DeliveryPartnerResponse;
import com.restaurantdelivery.entity.DeliveryPartnerProfile;
import com.restaurantdelivery.entity.User;

@Component
public class DeliveryPartnerMapper {

	public DeliveryPartnerProfile toEntity(DeliveryPartnerProfileRequest request, User user) {
		
		DeliveryPartnerProfile deliveryPartner = new DeliveryPartnerProfile();
		deliveryPartner.setUser(user);
		deliveryPartner.setVehicleType(request.getVehicleType());
		deliveryPartner.setVehicleNumber(request.getVehicleNumber());
		deliveryPartner.setLicenseNumber(request.getLicenseNumber());
		deliveryPartner.setAvailable(request.getAvailable());
		
		return deliveryPartner;
	}
	
	public DeliveryPartnerResponse toResponse(DeliveryPartnerProfile deliveryPartner) {
		
		DeliveryPartnerResponse response = new DeliveryPartnerResponse();
		response.setId(deliveryPartner.getId());
		response.setUserId(deliveryPartner.getUser().getId());
		response.setUserName(deliveryPartner.getUser().getFirstName()+" "+deliveryPartner.getUser().getLastName());
		response.setUserEmail(deliveryPartner.getUser().getEmail());
		response.setUserPhone(deliveryPartner.getUser().getPhoneNumber());
		response.setVehicleType(deliveryPartner.getVehicleType());
		response.setVehicleNumber(deliveryPartner.getVehicleNumber());
		response.setLicenseNumber(deliveryPartner.getLicenseNumber());
		response.setAvailable(deliveryPartner.getAvailable());
		response.setAverageRating(deliveryPartner.getAverageRating());
		response.setTotalReviews(deliveryPartner.getTotalReviews());
		response.setCreatedAt(deliveryPartner.getCreatedAt());
		response.setUpdatedAt(deliveryPartner.getUpdatedAt());
		
		return response;
	}
	
	public void updateEntity(DeliveryPartnerProfileRequest request,DeliveryPartnerProfile deliveryPartner, User user) {
		deliveryPartner.setUser(user);
		deliveryPartner.setVehicleType(request.getVehicleType());
		deliveryPartner.setVehicleNumber(request.getVehicleNumber());
		deliveryPartner.setLicenseNumber(request.getLicenseNumber());
		deliveryPartner.setAvailable(request.getAvailable());
	}
}
