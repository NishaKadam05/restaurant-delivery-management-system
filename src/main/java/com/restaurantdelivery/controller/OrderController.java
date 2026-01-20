package com.restaurantdelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantdelivery.dto.request.CancelOrderRequest;
import com.restaurantdelivery.dto.request.OrderRequest;
import com.restaurantdelivery.enums.OrderStatus;
import com.restaurantdelivery.enums.OrderType;
import com.restaurantdelivery.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/place")
	public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderRequest request){
		return orderService.placeOrder(request);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<?> getAllOrders(){
		return orderService.getAllOrders();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getOrderById(@PathVariable Long id){
		return orderService.getOrderById(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{customerId}")
	public ResponseEntity<?> getOrderByCustomerId(@PathVariable Long customerId){
		return orderService.getOrderByCustomerId(customerId);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{deliveryPartnerId}")
	public ResponseEntity<?> getOrderByDeliveryPartnerId(@PathVariable Long deliveryPartnerId){
		return orderService.getOrderByCustomerId(deliveryPartnerId);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{status}")
	public ResponseEntity<?> getOrderByStatus(@PathVariable OrderStatus status){
		return orderService.getOrderByStatus(status);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{type}")
	public ResponseEntity<?> getOrderByType(@PathVariable OrderType type){
		return orderService.getOrderByType(type);
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
	@PostMapping("/cancel")
	public ResponseEntity<?> cancelOrder(@Valid @RequestBody CancelOrderRequest request, Authentication authentication){
		return orderService.cancelOrder(request, authentication);
	}
	
	
}