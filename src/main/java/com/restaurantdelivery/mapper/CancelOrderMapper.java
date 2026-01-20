package com.restaurantdelivery.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.CancelOrderRequest;
import com.restaurantdelivery.dto.response.CancelOrderResponse;
import com.restaurantdelivery.entity.Order;
import com.restaurantdelivery.enums.OrderStatus;

@Component
public class CancelOrderMapper {

	public Order toOrderEntity(CancelOrderRequest request) {
		Order order = new Order();
		order.setOrderStatus(OrderStatus.CANCELLED);
		order.setCancelReason(request.getCancelReason());
		order.setCancelledAt(LocalDateTime.now());
		return order;
	}
	
	public CancelOrderResponse toCancelOrderResponse(Order order) {
		CancelOrderResponse response = new CancelOrderResponse();
		response.setId(order.getId());
		response.setOrderStatus(order.getOrderStatus());
		response.setCancelReason(order.getCancelReason());
		response.setCancelledAt(order.getCancelledAt());
		
		return response;
	}
	
	public void updateEntity(CancelOrderRequest request, Order order) {
		order.setOrderStatus(OrderStatus.CANCELLED);
		order.setCancelReason(request.getCancelReason());
		order.setCancelledAt(LocalDateTime.now());
	}
}
