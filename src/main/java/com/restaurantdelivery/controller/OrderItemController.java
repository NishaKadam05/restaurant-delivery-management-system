package com.restaurantdelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantdelivery.service.OrderItemService;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

	@Autowired
	private OrderItemService orderItemService;
	
	@PreAuthorize("hasRole('RESTAURANT_STAFF')")
	@GetMapping
	public ResponseEntity<?> getAllOrderItems(){
		return orderItemService.getAllOrderItems();
	}
	
	@PreAuthorize("hasRole('RESTAURANT_STAFF')")
	@GetMapping("/{orderId}")
	public ResponseEntity<?> getByOrderId(@PathVariable Long orderId){
		return orderItemService.getByOrderId(orderId);
	}
}
