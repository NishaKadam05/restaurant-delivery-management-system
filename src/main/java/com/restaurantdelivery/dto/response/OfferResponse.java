package com.restaurantdelivery.dto.response;

import java.time.LocalDateTime;

import com.restaurantdelivery.enums.DiscountType;

public class OfferResponse {

	private Long id;
    private String code;
    private String description;
    private DiscountType discountType;
    private double discountValue;
    private double minOrderAmount;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public DiscountType getDiscountType() {
		return discountType;
	}
	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}
	public double getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(double d) {
		this.discountValue = d;
	}
	public double getMinOrderAmount() {
		return minOrderAmount;
	}
	public void setMinOrderAmount(double minOrderAmount) {
		this.minOrderAmount = minOrderAmount;
	}
	public LocalDateTime getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(LocalDateTime validFrom) {
		this.validFrom = validFrom;
	}
	public LocalDateTime getValidUntil() {
		return validUntil;
	}
	public void setValidUntil(LocalDateTime validUntil) {
		this.validUntil = validUntil;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
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
