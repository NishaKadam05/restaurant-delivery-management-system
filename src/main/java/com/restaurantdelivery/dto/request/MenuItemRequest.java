package com.restaurantdelivery.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MenuItemRequest {

	@NotNull(message = "Category Id cannot be blank")
	private Long categoryId;
	
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	private String description;
	
	@NotNull(message = "Base price is required")
    @DecimalMin(value = "0.01", message = "Base price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Base price must have at most 2 decimal places")
	private double basePrice;
	
	@NotNull(message = "Mention if item is veg or not")
	private Boolean veg;
	
	private Boolean available;
	
	@NotNull(message = "Preparation Time cannot be null")
	private int preparationTime;

	
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public Boolean getVeg() {
		return veg;
	}

	public void setVeg(Boolean veg) {
		this.veg = veg;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public int getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}
	
	
}
