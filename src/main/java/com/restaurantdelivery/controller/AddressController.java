package com.restaurantdelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantdelivery.dto.request.AddressRequest;
import com.restaurantdelivery.enums.AddressType;
import com.restaurantdelivery.service.AddressService;

import jakarta.validation.Valid;

@RestController
@PreAuthorize("hasRole('CUSTOMER')")
@RequestMapping("/api/addresses")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@PostMapping
	public ResponseEntity<?> addAddress(@Valid @RequestBody AddressRequest request){
		return addressService.addAddress(request);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllAddresses(){
		return addressService.getAllAddresses();
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getAddressById(@PathVariable Long id){
		return addressService.getAddressById(id);
	}
	
	@GetMapping("/{addressType}")
	public ResponseEntity<?> getAddressByAddressType(@PathVariable AddressType addressType){
		return addressService.getAddressByAddressType(addressType);
	}
	
	@GetMapping("/default")
	public ResponseEntity<?> getDefautAddress(){
		return addressService.getDefaultAddress();
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateAddress(@Valid @RequestBody AddressRequest request,@PathVariable Long id){
		return addressService.updateAddress(request, id);
	}
	
	@PatchMapping("/updateDefault/{id}/{defaultAdd}")
	public ResponseEntity<?> updateIsDefault(@PathVariable Long id, @PathVariable Boolean defaultAdd){
		return addressService.updateIsDefault(id, defaultAdd);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteAddress(@PathVariable Long id){
		return addressService.deleteAddress(id);
	}
	
}
