package com.restaurantdelivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.restaurantdelivery.dto.request.OfferRequest;
import com.restaurantdelivery.dto.response.OfferResponse;
import com.restaurantdelivery.entity.Offer;
import com.restaurantdelivery.exception.BadRequestException;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.mapper.OfferMapper;
import com.restaurantdelivery.repository.OfferRepository;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {
 
    @Mock
    private OfferRepository offerRepository;
 
    @Mock
    private OfferMapper offerMapper;
 
    @InjectMocks
    private OfferService offerService;
    
    @Test
    void createOffer_success() {
     
        OfferRequest request = new OfferRequest();
        request.setCode("NEW50");
        request.setValidFrom(LocalDateTime.now());
        request.setValidUntil(LocalDateTime.now().plusDays(5));
     
        Offer offer = new Offer();
        OfferResponse response = new OfferResponse();
     
        when(offerRepository.existsByCodeIgnoreCase(request.getCode()))
                .thenReturn(false);
        when(offerMapper.toOfferEntity(request))
                .thenReturn(offer);
        when(offerRepository.save(offer))
                .thenReturn(offer);
        when(offerMapper.toOfferResponse(offer))
                .thenReturn(response);
     
        ResponseEntity<?> result = offerService.createOffer(request);
     
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
    
    @Test
    void getAllOffers_success() {
     
        List<Offer> offers = List.of(new Offer(), new Offer());
     
        when(offerRepository.findAll())
                .thenReturn(offers);
        when(offerMapper.toOfferResponse(any(Offer.class)))
                .thenReturn(new OfferResponse());
     
        ResponseEntity<?> response = offerService.getAllOffers();
     
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void deactivateOffer_success() {
     
        Offer offer = new Offer();
        offer.setId(1L);
        offer.setActive(true);
     
        when(offerRepository.findById(1L))
                .thenReturn(Optional.of(offer));
        when(offerRepository.save(offer))
                .thenReturn(offer);
     
        ResponseEntity<?> response = offerService.deactivateOffer(1L);
     
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(offer.getActive());
    }
    
    @Test
    void createOffer_offerCodeAlreadyExists() {
     
        OfferRequest request = new OfferRequest();
        request.setCode("SAVE20");
     
        when(offerRepository.existsByCodeIgnoreCase(request.getCode()))
                .thenReturn(true);
     
        assertThrows(BadRequestException.class,
                () -> offerService.createOffer(request));
    }
    
    @Test
    void updateOffer_offerNotFound() {
     
        OfferRequest request = new OfferRequest();
     
        when(offerRepository.findById(1L))
                .thenReturn(Optional.empty());
     
        assertThrows(ResourceNotFoundException.class,
                () -> offerService.updateOffer(request, 1L));
    }
    
    @Test
    void deactivateOffer_offerNotFound() {
     
        when(offerRepository.findById(1L))
                .thenReturn(Optional.empty());
     
        assertThrows(ResourceNotFoundException.class,
                () -> offerService.deactivateOffer(1L));
    }
}