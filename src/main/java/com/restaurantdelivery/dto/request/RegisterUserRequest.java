package com.restaurantdelivery.dto.request;

import com.restaurantdelivery.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterUserRequest {

	@Email(message = "Invalid Email format")
	@NotBlank(message = "Email is required")
	@Size(max = 150, message ="Email must not exceed 150 characters")
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min= 8, max = 20,  message = "Password must be between 8 and 20 characters")
	@Pattern(
	        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
	        message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character"
	    )
	private String password;

	@NotBlank(message = "First Name is required")
	@Size(max = 100, message = "First name must not exceed 100 characters")
	private String firstName;
	
	@NotBlank(message = "Last Name is required")
	@Size(max = 100, message = "Last name must not exceed 100 characters")
	private String lastName;
	
	@NotBlank(message = "Phone Number is required")
	@Pattern(regexp = "^[6-9]\\d{9}$",message = "Phone number must be a valid 10-digit Indian mobile number")
	private String phoneNumber;
	
	@NotNull(message = "Role is required")
	private UserRole role;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	
	
}
