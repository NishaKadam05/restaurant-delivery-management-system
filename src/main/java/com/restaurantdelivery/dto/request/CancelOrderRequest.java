package com.restaurantdelivery.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CancelOrderRequest {

	@NotNull(message = "Order Id is required")
	private Long id;
	
	@NotBlank(message = "Cancellation Reason is Required")
	private String cancelReason;

	public Long getId() {
		return id;
	}

	public void setOrderId(Long id) {
		this.id = id;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	
	
}
