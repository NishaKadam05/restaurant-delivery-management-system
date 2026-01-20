package com.restaurantdelivery.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.dto.response.OrderItemResponse;
import com.restaurantdelivery.entity.OrderItem;
import com.restaurantdelivery.mapper.OrderItemMapper;
import com.restaurantdelivery.repository.OrderItemRepository;
import com.restaurantdelivery.repository.OrderRepository;

@Service
public class OrderItemService {

	Logger logger = LoggerFactory.getLogger(OrderItemService.class);
	
	private final OrderItemRepository orderItemRepository;
	private final OrderRepository orderRepository;
	private final OrderItemMapper orderItemMapper;
	
	public OrderItemService(OrderItemRepository orderItemRepository, OrderRepository orderRepository,
			OrderItemMapper orderItemMapper) {
		super();
		this.orderItemRepository = orderItemRepository;
		this.orderRepository = orderRepository;
		this.orderItemMapper = orderItemMapper;
	}

	public ResponseEntity<?> getAllOrderItems(){
		
		logger.info("Getting the item list of all orders");
		List<OrderItem> orderItems = orderItemRepository.findAll();
		if(orderItems.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("OrderItems not added");
		}
		List<OrderItemResponse> orderItemList = new ArrayList<>();
		for(OrderItem orderItem: orderItems)
			orderItemList.add(orderItemMapper.toOrderItemResponse(orderItem));
		
		return ResponseEntity.ok(orderItemList);
	}
	
	public ResponseEntity<?> getByOrderId(Long orderId){
		
		logger.info("Getting the item list of a specific orders");
		List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
		if(orderItems.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("OrderItems not added");
		}
		List<OrderItemResponse> orderItemList = new ArrayList<>();
		for(OrderItem orderItem: orderItems)
			orderItemList.add(orderItemMapper.toOrderItemResponse(orderItem));
		
		return ResponseEntity.ok(orderItemList);
	}
}
