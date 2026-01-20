package com.restaurantdelivery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurantdelivery.entity.DeliveryAssignment;
import com.restaurantdelivery.enums.DeliveryStatus;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryAssignment, Long>{
	
	Optional<DeliveryAssignment> findByOrderId(Long orderId);
	
	List<DeliveryAssignment> findByDeliveryPartnerId(Long deliveryPartnerId);
	
	List<DeliveryAssignment> findByStatus(DeliveryStatus status);

}
