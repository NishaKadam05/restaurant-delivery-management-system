package com.restaurantdelivery.dto.request;

import com.restaurantdelivery.enums.AddressType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddressRequest {
	
	@NotNull(message = "User Id is required")
	private Long userId;

	@NotNull(message = "Address Type cannot be blank or null")
	private AddressType addressType;
	
	@NotBlank(message = "Address cannot be blank or null")
	private String addressLine1;
	
	private String addressLine2;
	
	@NotBlank(message = "City cannot be blank or null")
	private String city;
	
	@NotBlank(message = "State cannot be blank or null")
	private String state;
	
	@NotBlank(message = "Pincode cannot be blank or null")
	@Size(max=6, message = "Pincode must be 6 digits")
	private String pincode;
	
	@NotNull(message = "isDefault cannot be blank. Give true or false")
	private Boolean defaultAdd;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Boolean getDefaultAdd() {
		return defaultAdd;
	}

	public void setDefaultAdd(Boolean defaultAdd) {
		this.defaultAdd = defaultAdd;
	}
	
	
}
