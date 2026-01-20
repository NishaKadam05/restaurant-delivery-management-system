package com.restaurantdelivery.dto.request;

import com.restaurantdelivery.enums.VehicleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DeliveryPartnerProfileRequest {

	@NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Vehicle type is required")
    private VehicleType vehicleType;
    
    @NotBlank(message = "Vehicle number is required")
    @Size(min = 3, max = 20, message = "Vehicle number must be between 3-20 characters")
    @Pattern(
        regexp = "^[A-Z0-9\\-]+$",
        message = "Vehicle number must contain only uppercase letters, numbers, and hyphens"
    )
    private String vehicleNumber;
    
    @NotBlank(message = "License number is required")
    @Size(min = 5, max = 30, message = "License number must be between 5-30 characters")
    private String licenseNumber;
    
    private Boolean available = true;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
    
    
}
