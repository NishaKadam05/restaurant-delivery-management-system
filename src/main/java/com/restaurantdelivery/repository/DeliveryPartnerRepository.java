package com.restaurantdelivery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurantdelivery.entity.DeliveryPartnerProfile;

@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartnerProfile, Long>{
	
	Optional<DeliveryPartnerProfile> findByUserId(Long userId);
	
	boolean existsByVehicleNumber(String vehicleNumber);
	
	boolean existsByLicenseNumber(String licenseNumber);
	
	List<DeliveryPartnerProfile> findByAvailableTrue();

}
