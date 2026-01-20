package com.restaurantdelivery.mapper;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.PaymentRequest;
import com.restaurantdelivery.dto.response.PaymentResponse;
import com.restaurantdelivery.entity.Order;
import com.restaurantdelivery.entity.Payment;

@Component
public class PaymentMapper {

	public Payment toPaymentEntity(PaymentRequest request, Order order) {
		Payment payment = new Payment();
		payment.setOrder(order);
		
		return payment;
	}
	
	public PaymentResponse toPaymentResponse(Payment payment) {
		PaymentResponse response = new PaymentResponse();
		response.setId(payment.getId());
		response.setOrderId(payment.getOrder().getId());
		response.setPaymentMethod(payment.getPaymentMethod());
		response.setAmount(payment.getOrder().getTotalAmount());
		response.setPaymentStatus(payment.getPaymentStatus());
		response.setPaymentDate(payment.getPaymentDate());
		response.setCreatedAt(payment.getCreatedAt());
		response.setUpdatedAt(payment.getUpdatedAt());
		
		return response;
	}
}
