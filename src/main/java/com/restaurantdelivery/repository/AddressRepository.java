package com.restaurantdelivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurantdelivery.dto.response.AddressResponse;
import com.restaurantdelivery.entity.CustomerAddress;
import com.restaurantdelivery.enums.AddressType;

@Repository
public interface AddressRepository extends JpaRepository<CustomerAddress, Long> {

	Optional<CustomerAddress> findByAddressType(AddressType addressType);
	
	Optional<CustomerAddress> findByDefaultAdd(boolean DeafaultAdd);
	
	Optional<CustomerAddress> findByIdAndUserId(Long id, Long userId);
}
