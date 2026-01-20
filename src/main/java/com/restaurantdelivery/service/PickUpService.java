package com.restaurantdelivery.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.config.CurrentUserUtil;
import com.restaurantdelivery.dto.response.OrderResponse;
import com.restaurantdelivery.entity.Order;
import com.restaurantdelivery.entity.User;
import com.restaurantdelivery.enums.OrderStatus;
import com.restaurantdelivery.enums.OrderType;
import com.restaurantdelivery.exception.BadRequestException;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.exception.UnauthorizedException;
import com.restaurantdelivery.mapper.OrderMapper;
import com.restaurantdelivery.repository.OrderRepository;
import com.restaurantdelivery.repository.UserRepository;

@Service
public class PickUpService {
	
	Logger logger = LoggerFactory.getLogger(PickUpService.class);

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final OrderMapper orderMapper;
	
	public PickUpService(OrderRepository orderRepository, UserRepository userRepository, OrderMapper orderMapper) {
		super();
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.orderMapper = orderMapper;
	}
	
	public ResponseEntity<?> markPickedUp(Long orderId){
		
		logger.info("Marking order as picked up");
		
		User customer = userRepository.findByEmail(CurrentUserUtil.getCurrentUserEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
		
		if(order.getOrderType() != OrderType.SELF_PICKUP) {
			throw new BadRequestException("This is not a pickup order");
		}
		
		if(!order.getCustomer().getId().equals(customer.getId())) {
			throw new UnauthorizedException("You cannot pickup this order");

		}
		
		if(order.getOrderStatus() != OrderStatus.READY) {
			throw new BadRequestException("Order is not ready for pickup");
		}
		
		order.setOrderStatus(OrderStatus.PICKED_UP);
		OrderResponse response = orderMapper.toOrderResponse(orderRepository.save(order));
		
		return ResponseEntity.ok(response);
	}
}
