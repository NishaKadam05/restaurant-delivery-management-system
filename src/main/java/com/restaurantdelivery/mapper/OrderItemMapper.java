package com.restaurantdelivery.mapper;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.OrderItemRequest;
import com.restaurantdelivery.dto.response.OrderItemResponse;
import com.restaurantdelivery.entity.MenuItem;
import com.restaurantdelivery.entity.Order;
import com.restaurantdelivery.entity.OrderItem;

@Component
public class OrderItemMapper {

	public OrderItem toOrderItemEntity(OrderItemRequest request,Order order, MenuItem menuItem) {
		OrderItem orderItem = new OrderItem();
		orderItem.setOrder(order);
		orderItem.setMenuItem(menuItem);
		orderItem.setQuantity(request.getQuantity());
		orderItem.setSpecialInstructions(request.getSpecialInstructions());
		
		return orderItem;
	}
	
	public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
		OrderItemResponse response = new OrderItemResponse();
		response.setId(orderItem.getId());
		response.setOrderId(orderItem.getOrder().getId());
		response.setMenuItemId(orderItem.getMenuItem().getId());
		response.setMenuItemName(orderItem.getMenuItem().getName());
		response.setQuantity(orderItem.getQuantity());
		response.setUnitPrice(orderItem.getUnitPrice());
		response.setSpecialInstructions(orderItem.getSpecialInstructions());
		response.setCreatedAt(orderItem.getCreatedAt());
		
		return response;
	}
}
