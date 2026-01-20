package com.restaurantdelivery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurantdelivery.entity.Order;
import com.restaurantdelivery.entity.Payment;
import com.restaurantdelivery.enums.PaymentMethod;
import com.restaurantdelivery.enums.PaymentStatus;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

	Optional<Payment> findByOrderId(Long orderId);
	
	List<Payment> findByPaymentStatus(PaymentStatus status);
	
	Payment findByOrder(Order order);
	
	List<Payment> findByPaymentMethod(PaymentMethod method);
}
