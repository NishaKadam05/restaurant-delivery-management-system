package com.restaurantdelivery.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.dto.request.DeliveryAssignmentRequest;
import com.restaurantdelivery.dto.response.DeliveryAssignmentResponse;
import com.restaurantdelivery.entity.DeliveryAssignment;
import com.restaurantdelivery.entity.Order;
import com.restaurantdelivery.entity.User;
import com.restaurantdelivery.enums.DeliveryStatus;
import com.restaurantdelivery.enums.OrderStatus;
import com.restaurantdelivery.enums.OrderType;
import com.restaurantdelivery.enums.UserRole;
import com.restaurantdelivery.exception.BadRequestException;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.mapper.DeliveryAssignmentMapper;
import com.restaurantdelivery.repository.DeliveryRepository;
import com.restaurantdelivery.repository.OrderRepository;
import com.restaurantdelivery.repository.UserRepository;

@Service
public class DeliveryAssignmentService {
	
	Logger logger = LoggerFactory.getLogger(DeliveryAssignmentService.class);

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final DeliveryRepository deliveryRepository;
	private final DeliveryAssignmentMapper deliveryMapper;
	
	public DeliveryAssignmentService(OrderRepository orderRepository, UserRepository userRepository,
			DeliveryRepository deliveryRepository, DeliveryAssignmentMapper deliveryMapper) {
		super();
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.deliveryRepository = deliveryRepository;
		this.deliveryMapper = deliveryMapper;
	}



	public ResponseEntity<?> assignDelivery(DeliveryAssignmentRequest request){
		logger.info("Assigning delivery to a delivery partner");
		Order order= orderRepository.findById(request.getOrderId())
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
		order.setOrderStatus(OrderStatus.READY);
		
		if(order.getOrderType() == OrderType.SELF_PICKUP) {
			throw new BadRequestException("Pickup order does not require delivery");
		}
		
		if(order.getOrderStatus() != OrderStatus.READY) {
			throw new BadRequestException("Order must be READY for delivery");
		}
		
		User customer = userRepository.findById(request.getCustomerId())
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		
		User deliveryPartner = userRepository.findById(request.getDeliveryPartnerId())
				.orElseThrow(() -> new ResourceNotFoundException("Delivery Partner not found"));
		
		if(!deliveryPartner.getRole().equals(UserRole.DELIVERY_PARTNER)) {
			throw new BadRequestException("User is not a delivery Partner");
		}
		
		order.setDeliveryPartner(deliveryPartner);
		order.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
		
		DeliveryAssignment deliveryAssignment = deliveryMapper.toDeliveryAssignmentEntity(request, order, deliveryPartner, customer);
		order.setDeliveryAssignment(deliveryAssignment);
		deliveryAssignment.setAssignedAt(LocalDateTime.now());
		DeliveryAssignment savedDeliveryAssignment = deliveryRepository.save(deliveryAssignment);
		DeliveryAssignmentResponse response = deliveryMapper.toDeliveryAssignmentResponse(savedDeliveryAssignment);
		
		return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<?> getAllDeliveryAssignments(){
		logger.info("Getting all delivery assignments");
		List<DeliveryAssignment> deliveryAssignments = deliveryRepository.findAll();
		if(deliveryAssignments.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("No deliveries assigned");
		}
		
		List<DeliveryAssignmentResponse> deliveryAssignmentList = new ArrayList<>();
		for(DeliveryAssignment deliveryAssignment: deliveryAssignments)
			deliveryAssignmentList.add(deliveryMapper.toDeliveryAssignmentResponse(deliveryAssignment));
		
		return ResponseEntity.ok(deliveryAssignmentList);
	}
	
	
	public ResponseEntity<?> getByOrderId(Long orderId){
		logger.info("Getting delivery assignments by order id "+orderId);
		DeliveryAssignment deliveryAssignment = deliveryRepository.findByOrderId(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Delivery Assignment not found"));
		DeliveryAssignmentResponse response = deliveryMapper.toDeliveryAssignmentResponse(deliveryAssignment);
		
		return ResponseEntity.ok(response);
		
	}
	
	public ResponseEntity<?> getByDeliveryPartnerId(Long deliveryPartnerId){
		logger.info("Getting delivery assignments by delivery partner id "+deliveryPartnerId);
		List<DeliveryAssignment> deliveryAssignments = deliveryRepository.findByDeliveryPartnerId(deliveryPartnerId);
		if(deliveryAssignments.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("No deliveries assigned");
		}
		
		List<DeliveryAssignmentResponse> deliveryAssignmentList = new ArrayList<>();
		for(DeliveryAssignment deliveryAssignment: deliveryAssignments)
			deliveryAssignmentList.add(deliveryMapper.toDeliveryAssignmentResponse(deliveryAssignment));
		
		return ResponseEntity.ok(deliveryAssignmentList);
	}
	
	public ResponseEntity<?> getByStatus(DeliveryStatus status){
		logger.info("Getting delivery assignments by delivery status ");
		List<DeliveryAssignment> deliveryAssignments = deliveryRepository.findByStatus(status);
		if(deliveryAssignments.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("No deliveries assigned");
		}
		
		List<DeliveryAssignmentResponse> deliveryAssignmentList = new ArrayList<>();
		for(DeliveryAssignment deliveryAssignment: deliveryAssignments)
			deliveryAssignmentList.add(deliveryMapper.toDeliveryAssignmentResponse(deliveryAssignment));
		
		return ResponseEntity.ok(deliveryAssignmentList);
	}

}
