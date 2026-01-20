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

import com.restaurantdelivery.dto.request.PaymentRequest;
import com.restaurantdelivery.enums.PaymentMethod;
import com.restaurantdelivery.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping
	public ResponseEntity<?> makePayment(@RequestBody PaymentRequest request){
		return paymentService.makePayment(request);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/refund/{orderId}")
	public ResponseEntity<?> refundPayment(@PathVariable Long orderId){
		return paymentService.refundPayment(orderId);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<?> getAllPayments(){
		return paymentService.getAllPayments();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
	@GetMapping("/ByOrder/{orderId}")
	public ResponseEntity<?> getPaymentByOrder(@PathVariable Long orderId){
		return paymentService.getPaymentByOrder(orderId);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/ByPaymentMethod/{method}")
	public ResponseEntity<?> getPaymentByMethod(@PathVariable PaymentMethod method){
		return paymentService.getPaymentByMethod(method);
	}

}
