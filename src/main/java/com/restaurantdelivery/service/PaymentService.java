package com.restaurantdelivery.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.config.CurrentUserUtil;
import com.restaurantdelivery.dto.request.PaymentRequest;
import com.restaurantdelivery.dto.response.PaymentResponse;
import com.restaurantdelivery.entity.Order;
import com.restaurantdelivery.entity.Payment;
import com.restaurantdelivery.entity.User;
import com.restaurantdelivery.enums.OrderStatus;
import com.restaurantdelivery.enums.PaymentMethod;
import com.restaurantdelivery.enums.PaymentStatus;
import com.restaurantdelivery.exception.BadRequestException;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.exception.UnauthorizedException;
import com.restaurantdelivery.mapper.PaymentMapper;
import com.restaurantdelivery.repository.OrderRepository;
import com.restaurantdelivery.repository.PaymentRepository;
import com.restaurantdelivery.repository.UserRepository;

@Service
public class PaymentService {
	
	Logger logger = LoggerFactory.getLogger(PaymentService.class);

	private final PaymentRepository paymentRepository;
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final PaymentMapper paymentMapper;
	
	public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository,
			UserRepository userRepository, PaymentMapper paymentMapper) {
		super();
		this.paymentRepository = paymentRepository;
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.paymentMapper = paymentMapper;
	}

	public ResponseEntity<?> makePayment(PaymentRequest request){
		
		logger.info("Attempting to make payment");
		User customer = userRepository.findByEmail(CurrentUserUtil.getCurrentUserEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		Order order = orderRepository.findById(request.getOrderId())
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
		
		if(!order.getCustomer().getId().equals(customer.getId())) {
			throw new UnauthorizedException("You cannot pay for this order");
		}
		
		if(order.getTotalAmount() <= 0) {
			throw new BadRequestException("Order amount is invalid");
		}
		
		if(order.getOrderStatus() != OrderStatus.PLACED) {
			throw new BadRequestException("Order already paid or processed");
		}
		
		Payment payment = paymentMapper.toPaymentEntity(request, order);
		payment.setPaymentMethod(order.getPaymentMethod());
		payment.setPaymentStatus(PaymentStatus.COMPLETED);
		payment.setPaymentDate(LocalDateTime.now());
		Payment savedPayment = paymentRepository.save(payment);
		
		PaymentResponse response = paymentMapper.toPaymentResponse(savedPayment);
		return ResponseEntity.ok(response);
	}
	
	
	public ResponseEntity<?> getAllPayments(){
		List<Payment> payments = paymentRepository.findAll();
		if(payments.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("No Payments made");
		}
		
		List<PaymentResponse> paymentList = new ArrayList<>();
		for(Payment payment:payments)
			paymentList.add(paymentMapper.toPaymentResponse(payment));
		
		return ResponseEntity.ok(paymentList);
	}
	
	
	public ResponseEntity<?> getPaymentByOrder(Long orderId){
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order Not Found"));
		
		Payment payment = paymentRepository.findByOrder(order);
		PaymentResponse response = paymentMapper.toPaymentResponse(payment);
		return ResponseEntity.ok(response);
	}
	
	
	public ResponseEntity<?> getPaymentByMethod(PaymentMethod method){
		List<Payment> payments = paymentRepository.findByPaymentMethod(method);
		if(payments.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("No Payments made by "+method);
		}
		
		List<PaymentResponse> paymentList = new ArrayList<>();
		for(Payment payment:payments)
			paymentList.add(paymentMapper.toPaymentResponse(payment));
		
		return ResponseEntity.ok(paymentList);
	}
	
	
	public ResponseEntity<?> refundPayment(Long orderId){
		
		logger.info("Attempting to refund payment");
		Payment payment = paymentRepository.findByOrderId(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
		
		if(payment.getPaymentStatus() != PaymentStatus.COMPLETED) {
			throw new BadRequestException("Payment not allowed for payment status: "+ payment.getPaymentStatus());
		}
		
		payment.setPaymentStatus(PaymentStatus.REFUNDED);
		
		paymentRepository.save(payment);
		
		return ResponseEntity.ok("Payment Refunded successfully!");
	}
}
