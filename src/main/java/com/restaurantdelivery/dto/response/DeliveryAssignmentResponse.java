package com.restaurantdelivery.dto.response;

import java.time.LocalDateTime;

import com.restaurantdelivery.entity.CustomerAddress;
import com.restaurantdelivery.enums.DeliveryStatus;

public class DeliveryAssignmentResponse {

	private Long id;
    private Long orderId;
    private Long deliveryPartnerId;
    private String deliveryPartnerName;
    private String deliveryPartnerPhone;
    private AddressResponse deliveryAddress;
    private String customerName;
    private String customerPhone;
    private DeliveryStatus status;
    private Double totalAmount;
    private Double deliveryFee;
    private LocalDateTime assignedAt;
    
	public LocalDateTime getAssignedAt() {
		return assignedAt;
	}
	public void setAssignedAt(LocalDateTime assignedAt) {
		this.assignedAt = assignedAt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getDeliveryPartnerId() {
		return deliveryPartnerId;
	}
	public void setDeliveryPartnerId(Long deliveryPartnerId) {
		this.deliveryPartnerId = deliveryPartnerId;
	}
	public String getDeliveryPartnerName() {
		return deliveryPartnerName;
	}
	public void setDeliveryPartnerName(String deliveryPartnerName) {
		this.deliveryPartnerName = deliveryPartnerName;
	}
	public String getDeliveryPartnerPhone() {
		return deliveryPartnerPhone;
	}
	public void setDeliveryPartnerPhone(String deliveryPartnerPhone) {
		this.deliveryPartnerPhone = deliveryPartnerPhone;
	}
	public AddressResponse getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(AddressResponse customerAddress) {
		this.deliveryAddress = customerAddress;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public DeliveryStatus getStatus() {
		return status;
	}
	public void setStatus(DeliveryStatus status) {
		this.status = status;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Double getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
    
    
}
