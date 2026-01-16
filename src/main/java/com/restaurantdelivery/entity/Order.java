package com.restaurantdelivery.entity;

import java.time.LocalDateTime;

import com.restaurantdelivery.enums.OrderStatus;
import com.restaurantdelivery.enums.OrderType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String orderNumber;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private User customer;
	
	@ManyToOne
	@JoinColumn(name = "delivery_address_id")
	private CustomerAddress deliveryAddress;
	
	@ManyToOne
	@JoinColumn(name = "delivery_partner_id")
	private User deliveryPartner;
	
	@Enumerated(EnumType.STRING)
	private OrderType orderType;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	private double subtotal;
	private double deliveryFee;
	private double taxAmount;
	private double discountAmount;
	private double totalAmount;
	
	@ManyToOne
	@JoinColumn(name = "offer_id")
	private Offer offer;
	
	private String specialInstructions;
	private int estimatedPreparationTime;
	private LocalDateTime estimatedDeliveryTime;
	
	private LocalDateTime createdAt;	
	private LocalDateTime updatedAt;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public User getCustomer() {
		return customer;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	public CustomerAddress getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(CustomerAddress deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public User getDeliveryPartner() {
		return deliveryPartner;
	}
	public void setDeliveryPartner(User deliveryPartner) {
		this.deliveryPartner = deliveryPartner;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public double getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	public int getEstimatedPreparationTime() {
		return estimatedPreparationTime;
	}
	public void setEstimatedPreparationTime(int estimatedPreparationTime) {
		this.estimatedPreparationTime = estimatedPreparationTime;
	}
	public LocalDateTime getEstimatedDeliveryTime() {
		return estimatedDeliveryTime;
	}
	public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
		this.estimatedDeliveryTime = estimatedDeliveryTime;
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
