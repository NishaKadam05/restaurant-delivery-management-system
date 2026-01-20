package com.restaurantdelivery.mapper;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.OfferRequest;
import com.restaurantdelivery.dto.response.OfferResponse;
import com.restaurantdelivery.entity.Offer;

@Component
public class OfferMapper {

	public Offer toOfferEntity(OfferRequest request) {
		Offer offer = new Offer();
        offer.setCode(request.getCode());
        offer.setDescription(request.getDescription());
        offer.setDiscountType(request.getDiscountType());
        offer.setDiscountValue(request.getDiscountValue());
        offer.setMinOrderAmount(request.getMinOrderAmount());
        offer.setValidFrom(request.getValidFrom());
        offer.setValidUntil(request.getValidUntil());
        offer.setActive(request.getActive());
        
        return offer;
	}
	
	public OfferResponse toOfferResponse(Offer offer) {
		OfferResponse response = new OfferResponse();
		response.setId(offer.getId());
		response.setCode(offer.getCode());
		response.setDescription(offer.getDescription());
		response.setDiscountType(offer.getDiscountType());
		response.setDiscountValue(offer.getDiscountValue());
		response.setMinOrderAmount(offer.getMinOrderAmount());
		response.setValidFrom(offer.getValidFrom());
		response.setValidUntil(offer.getValidUntil());
		response.setActive(offer.getActive());
		response.setCreatedAt(offer.getCreatedAt());
		response.setUpdatedAt(offer.getUpdatedAt());
		
		return response;
	}
	
	public void updateEntity(OfferRequest request, Offer offer) {
		offer.setCode(request.getCode());
        offer.setDescription(request.getDescription());
        offer.setDiscountType(request.getDiscountType());
        offer.setDiscountValue(request.getDiscountValue());
        offer.setMinOrderAmount(request.getMinOrderAmount());
        offer.setValidFrom(request.getValidFrom());
        offer.setValidUntil(request.getValidUntil());
        offer.setActive(request.getActive());
	}
}
