package com.restaurantdelivery.entity;

import java.time.LocalDateTime;

import com.restaurantdelivery.enums.DeliveryStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DeliveryAssignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "delivery_partner_id")
	private User deliveryPartner;
	
	private LocalDateTime assignedAt;
	private LocalDateTime acceptedAt;
	private LocalDateTime pickedUpAt;
	private LocalDateTime deliveredAt;
	
	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;
	
	private String rejectionReason;
	
	private LocalDateTime createdAt;	
	private LocalDateTime updatedAt;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public User getDeliveryPartner() {
		return deliveryPartner;
	}
	public void setDeliveryPartner(User deliveryPartner) {
		this.deliveryPartner = deliveryPartner;
	}
	public LocalDateTime getAssignedAt() {
		return assignedAt;
	}
	public void setAssignedAt(LocalDateTime assignedAt) {
		this.assignedAt = assignedAt;
	}
	public LocalDateTime getAcceptedAt() {
		return acceptedAt;
	}
	public void setAcceptedAt(LocalDateTime acceptedAt) {
		this.acceptedAt = acceptedAt;
	}
	public LocalDateTime getPickedUpAt() {
		return pickedUpAt;
	}
	public void setPickedUpAt(LocalDateTime pickedUpAt) {
		this.pickedUpAt = pickedUpAt;
	}
	public LocalDateTime getDeliveredAt() {
		return deliveredAt;
	}
	public void setDeliveredAt(LocalDateTime deliveredAt) {
		this.deliveredAt = deliveredAt;
	}
	public DeliveryStatus getStatus() {
		return status;
	}
	public void setStatus(DeliveryStatus status) {
		this.status = status;
	}
	public String getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
