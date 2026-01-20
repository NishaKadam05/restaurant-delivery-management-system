package com.restaurantdelivery.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.dto.request.AddressRequest;
import com.restaurantdelivery.dto.response.AddressResponse;
import com.restaurantdelivery.entity.CustomerAddress;
import com.restaurantdelivery.entity.User;
import com.restaurantdelivery.enums.AddressType;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.mapper.AddressMapper;
import com.restaurantdelivery.repository.AddressRepository;
import com.restaurantdelivery.repository.UserRepository;

@Service
public class AddressService {
	
	Logger logger = LoggerFactory.getLogger(AddressService.class);

	private final AddressRepository addressRepository;
	private final AddressMapper addressMapper;
	private final UserRepository userRepository;
	
	public AddressService(AddressRepository addressRepository, AddressMapper addressMapper, UserRepository userRepository) {
		super();
		this.addressRepository = addressRepository;
		this.addressMapper = addressMapper;
		this.userRepository=userRepository;
	}
	
	public ResponseEntity<?> addAddress(AddressRequest request){
		
		logger.info("Adding address");
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		CustomerAddress address = addressMapper.toAddressEntity(request,user);
		CustomerAddress savedAddress = addressRepository.save(address);
		AddressResponse response = addressMapper.toAddressResponse(savedAddress);
		logger.info("New Address added successfully");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	public ResponseEntity<?> getAllAddresses(){
		logger.info("Getting all addressess By Id");
		List<CustomerAddress> addresses = addressRepository.findAll();
		
		if(addresses.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("No Addresses added");
		}
		
		List<AddressResponse> addressList = new ArrayList<>();
		for(CustomerAddress address: addresses) {
			addressList.add(addressMapper.toAddressResponse(address));
		}
		logger.info("Got all addressess");
		return ResponseEntity.ok(addressList);
	}
	
	
	public ResponseEntity<?> getAddressById(Long id){
		logger.info("Getting Address By Id "+id);
		CustomerAddress address = addressRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Address Not Found with the Id "+id));
		
		return ResponseEntity.ok(addressMapper.toAddressResponse(address));
	}
	
	
	public ResponseEntity<?> getAddressByAddressType(AddressType addressType){
		logger.info("Getting Address by Address Type "+addressType);
		CustomerAddress address = addressRepository.findByAddressType(addressType)
				.orElseThrow(()-> new ResourceNotFoundException("Address Not Found with the address type "+ addressType));
		
		return ResponseEntity.ok(addressMapper.toAddressResponse(address));
	}
	
	
	public ResponseEntity<?> getDefaultAddress(){
		logger.info("Getting default address");
		CustomerAddress address = addressRepository.findByDefaultAdd(true)
				.orElseThrow(()-> new ResourceNotFoundException("Default Address not found"));
		
		return ResponseEntity.ok(addressMapper.toAddressResponse(address)); 
				
	}
	
	
	public ResponseEntity<?> updateAddress(AddressRequest request,Long id){
		logger.info("Updating address");
		
		CustomerAddress address = addressRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Address Not Found with the Id "+ id));
		
		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		addressMapper.updateEntity(request,address,user);;
		addressRepository.save(address);
		AddressResponse response = addressMapper.toAddressResponse(address);
		
		logger.info("Updated Address");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<?> updateIsDefault(Long id, Boolean DefaultAdd){
		logger.info("Updating the default status of an address");
		CustomerAddress address = addressRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Address Not Found with the Id "+ id));
		
		address.setDefaultAdd(DefaultAdd);
		AddressResponse response = addressMapper.toAddressResponse(addressRepository.save(address));
		
		return ResponseEntity.ok(response);

	}
	
	public ResponseEntity<?> deleteAddress(Long id){
		logger.info("Deleting Address");
		CustomerAddress address = addressRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Address Not Found with the Id "+ id));
		
		addressRepository.delete(address);
		
		return ResponseEntity.ok("Address deleted successfully");
	}
	
}
