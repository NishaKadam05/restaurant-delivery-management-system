package com.restaurantdelivery.dto.response;

import java.time.LocalDateTime;

import com.restaurantdelivery.enums.OrderStatus;

public class CancelOrderResponse {

	private Long id;
	private OrderStatus orderStatus;
	private String cancelReason;
	private LocalDateTime cancelledAt;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public LocalDateTime getCancelledAt() {
		return cancelledAt;
	}
	public void setCancelledAt(LocalDateTime cancelledAt) {
		this.cancelledAt = cancelledAt;
	}
	
	
}
