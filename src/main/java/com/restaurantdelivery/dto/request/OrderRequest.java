package com.restaurantdelivery.dto.request;

import java.util.List;

import com.restaurantdelivery.enums.OrderType;
import com.restaurantdelivery.enums.PaymentMethod;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OrderRequest {

	@NotNull(message = "Order Type is required")
	private OrderType orderType;
	
	private Long deliveryAddressId;
	
	@NotEmpty(message = "Order must contain at least one item")
    @Valid
	private List<OrderItemRequest> items;
	
	private String offerCode;
	
    @Size(max = 500, message = "Special instructions must not exceed 500 characters")
	private String specialInstructions;
	
    @NotNull(message = "Payment method is required")
	private PaymentMethod paymentMethod;

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Long getDeliveryAddressId() {
		return deliveryAddressId;
	}

	public void setDeliveryAddressId(Long deliveryAddressId) {
		this.deliveryAddressId = deliveryAddressId;
	}

	public List<OrderItemRequest> getItems() {
		return items;
	}

	public void setItems(List<OrderItemRequest> items) {
		this.items = items;
	}

	public String getOfferCode() {
		return offerCode;
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	public String getSpecialInstructions() {
		return specialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
}
