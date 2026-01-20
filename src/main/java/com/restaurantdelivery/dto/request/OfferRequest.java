package com.restaurantdelivery.dto.request;

import java.time.LocalDateTime;

import com.restaurantdelivery.enums.DiscountType;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OfferRequest {

	@NotBlank(message = "Code cannot be blank")
	@Size(min = 2, max=30)
	private String code;
	
	private String description;
	
	@NotNull(message = "Discount Type cannot be blank")
	private DiscountType discountType;
	
	@NotNull(message = "Discount Value cannot be null")
	private double discountValue;
	
	@NotNull(message = "Minimum order amount cannot be null")
	private double minOrderAmount;
	
	private LocalDateTime validFrom;
	
	@Future
	private LocalDateTime validUntil;
	
	private Boolean active;
	
	
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
	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
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
	
	
}
