package com.restaurantdelivery.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.dto.request.OfferRequest;
import com.restaurantdelivery.dto.response.OfferResponse;
import com.restaurantdelivery.entity.Offer;
import com.restaurantdelivery.exception.BadRequestException;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.mapper.OfferMapper;
import com.restaurantdelivery.repository.OfferRepository;

@Service
public class OfferService {
	
	Logger logger = LoggerFactory.getLogger(OfferService.class);

	private final OfferRepository offerRepository;
	private final OfferMapper offerMapper;

	public OfferService(OfferRepository offerRepository, OfferMapper offerMapper) {
		super();
		this.offerRepository = offerRepository;
		this.offerMapper = offerMapper;
	}

	public ResponseEntity<?> createOffer(OfferRequest request) {
		
		logger.info("Creating an offer");

		if (offerRepository.existsByCodeIgnoreCase(request.getCode())) {
			throw new BadRequestException("Offer code already exists");
		}

		if (request.getValidUntil().isBefore(request.getValidFrom())) {
			throw new BadRequestException("Valid Until must be after valid from");
		}

		Offer offer = offerMapper.toOfferEntity(request);
		OfferResponse response = offerMapper.toOfferResponse(offerRepository.save(offer));

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<?> updateOffer(OfferRequest request, Long id) {
		
		logger.info("Updating an offer");

		Offer offer = offerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Offer not found"));

		if (request.getValidUntil().isBefore(request.getValidFrom())) {
			throw new BadRequestException("Valid Until must be after valid from");
		}

		offerMapper.updateEntity(request,offer);
		OfferResponse response = offerMapper.toOfferResponse(offerRepository.save(offer));

		return ResponseEntity.ok(response);
	}

	public ResponseEntity<?> getAllOffers() {

		List<Offer> offers = offerRepository.findAll();

		if (offers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No Offers added");
		}
		List<OfferResponse> offerList = new ArrayList<>();
		for (Offer offer : offers) {
			offerList.add(offerMapper.toOfferResponse(offer));
		}

		return ResponseEntity.ok(offerList);

	}

	public ResponseEntity<?> deactivateOffer(Long id) {
		
		logger.info("Deactivating an offer");

		Offer offer = offerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Offer not found"));

		offer.setActive(false);
		offerRepository.save(offer);

		return ResponseEntity.ok("Offer deactivated successfully");

	}

	public ResponseEntity<?> getActiveOffers() {

		List<Offer> offers = offerRepository.findByActiveTrue();

		if (offers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No Offers added");
		}
		List<OfferResponse> offerList = new ArrayList<>();
		for (Offer offer : offers) {
			offerList.add(offerMapper.toOfferResponse(offer));
		}

		return ResponseEntity.ok(offerList);

	}

}
