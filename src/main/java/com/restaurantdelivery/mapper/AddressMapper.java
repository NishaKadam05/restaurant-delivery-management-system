package com.restaurantdelivery.mapper;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.AddressRequest;
import com.restaurantdelivery.dto.response.AddressResponse;
import com.restaurantdelivery.entity.CustomerAddress;
import com.restaurantdelivery.entity.User;

@Component
public class AddressMapper {

	public CustomerAddress toAddressEntity(AddressRequest request, User user) {
		 CustomerAddress address = new CustomerAddress();
		 address.setUser(user);
		 address.setAddressType(request.getAddressType());
		 address.setAddressLine1(request.getAddressLine1());
		 address.setAddressLine2(request.getAddressLine2());
		 address.setCity(request.getCity());
		 address.setState(request.getState());
		 address.setPincode(request.getPincode());
		 address.setDefaultAdd(request.getDefaultAdd());
		
		 return address;
	}
	
	public AddressResponse toAddressResponse(CustomerAddress address) {
		AddressResponse response = new AddressResponse();
		response.setId(address.getId());
		response.setUserId(address.getUser().getId());
		response.setAddressType(address.getAddressType());
		response.setAddressLine1(address.getAddressLine1());
		response.setAddressLine2(address.getAddressLine2());
		response.setCity(address.getCity());
		response.setState(address.getState());
		response.setPincode(address.getPincode());
		response.setDefaultAdd(address.getDefaultAdd());
		response.setCreatedAt(address.getCreatedAt());
		response.setUpdatedAt(address.getUpdatedAt());
		
		return response;
	}
	
	public void updateEntity(AddressRequest request,CustomerAddress address, User user) {
		
		 address.setUser(user);
		 address.setAddressType(request.getAddressType());
		 address.setAddressLine1(request.getAddressLine1());
		 address.setAddressLine2(request.getAddressLine2());
		 address.setCity(request.getCity());
		 address.setState(request.getState());
		 address.setPincode(request.getPincode());
		 address.setDefaultAdd(request.getDefaultAdd());
	
	}
}
