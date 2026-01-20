package com.restaurantdelivery.dto.request;

import java.time.LocalTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RestaurantRequest {

	@NotBlank(message = "Restaurant name cannot be blank")
	private String name;
	
	private String description;	
	
	@NotBlank(message = "Phone Number cannot be blank or null")
	@Pattern(regexp = "^[6-9]\\d{9}$",message = "Phone number must be a valid 10-digit Indian mobile number")
	private String phoneNumber;
	
	@Email(message = "Invalid Email")
	@NotBlank(message = "Email is required")
	private String email;	
	
	@NotBlank(message = "Street Address is required")
	private String streetAddress;
	
	@NotBlank(message = "City is required")
	private String city;
	
	@NotBlank(message = "State is required")
	private String state;
	
	@NotBlank(message = "Pincode is required")
	private String pincode;
	
	private LocalTime openingTime;
	private LocalTime closingTime;
	
	private Boolean open;
	
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
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
	public LocalTime getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(LocalTime openingTime) {
		this.openingTime = openingTime;
	}
	public LocalTime getClosingTime() {
		return closingTime;
	}
	public void setClosingTime(LocalTime closingTime) {
		this.closingTime = closingTime;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	
}
