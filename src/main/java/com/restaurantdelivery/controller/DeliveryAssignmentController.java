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

import com.restaurantdelivery.dto.request.DeliveryAssignmentRequest;
import com.restaurantdelivery.enums.DeliveryStatus;
import com.restaurantdelivery.service.DeliveryAssignmentService;

@RestController
@RequestMapping("/api/delivery-assignment")
public class DeliveryAssignmentController {

	@Autowired
	private DeliveryAssignmentService deliveryAssignmentService;
	
	@PreAuthorize("hasAnyRole('RESTAURANT_STAFF','ADMIN')")
	@PostMapping("/assign")
	public ResponseEntity<?> assignDelivery(@RequestBody DeliveryAssignmentRequest request){
		return deliveryAssignmentService.assignDelivery(request);
	}
	
	@PreAuthorize("hasAnyRole('RESTAURANT_STAFF','ADMIN')")
	@GetMapping
	public ResponseEntity<?> getAllDeliveryAssignments(){
		return deliveryAssignmentService.getAllDeliveryAssignments();
	}
	
	@PreAuthorize("hasAnyRole('RESTAURANT_STAFF','ADMIN')")
	@GetMapping("orderId/{orderId}")
	public ResponseEntity<?> getByOrderId(@PathVariable Long orderId){
		return deliveryAssignmentService.getByOrderId(orderId);
	}
	
	@PreAuthorize("hasAnyRole('RESTAURANT_STAFF','ADMIN','DELIVERY_PARTNER')")
	@GetMapping("/{deliveryPartnerId}")
	public ResponseEntity<?> getByDeliveryPartnerId(@PathVariable Long deliveryPartnerId){
		return deliveryAssignmentService.getByDeliveryPartnerId(deliveryPartnerId);
	}
	
	@PreAuthorize("hasAnyRole('RESTAURANT_STAFF','ADMIN')")
	@GetMapping("status/{status}")
	public ResponseEntity<?> getByStatus(@PathVariable DeliveryStatus status){
		return deliveryAssignmentService.getByStatus(status);
	}

}
