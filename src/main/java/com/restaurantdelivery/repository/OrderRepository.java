package com.restaurantdelivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurantdelivery.entity.Order;
import com.restaurantdelivery.enums.OrderStatus;
import com.restaurantdelivery.enums.OrderType;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	
	List<Order> findByCustomerId(Long customerId);
	
	List<Order> findByOrderStatus(OrderStatus status);
	
	List<Order> findByDeliveryPartnerId(Long deliveryPartnerId);
	
	List<Order> findByOrderType(OrderType type);

}
