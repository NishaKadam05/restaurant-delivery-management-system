package com.restaurantdelivery.dto.request;

import jakarta.validation.constraints.NotNull;

public class DeliveryAssignmentRequest {

    @NotNull(message = "Order ID is required")
    private Long orderId;
    
    @NotNull(message = "Delivery partner ID is required")
    private Long deliveryPartnerId;
    
    @NotNull(message = "Customer Id is required")
    private Long customerId;
    

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getDeliveryPartnerId() {
		return deliveryPartnerId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public void setDeliveryPartnerId(Long deliveryPartnerId) {
		this.deliveryPartnerId = deliveryPartnerId;
	}
    
    
}
