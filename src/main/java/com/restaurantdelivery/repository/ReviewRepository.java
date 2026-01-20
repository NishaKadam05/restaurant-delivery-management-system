package com.restaurantdelivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurantdelivery.entity.Review;
import com.restaurantdelivery.enums.ReviewType;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	boolean existsByRestaurantIdAndCustomerId(Long restaurantId, Long customerId);
	
	List<Review> findByMenuItemId(Long menuItemId);
	
	List<Review> findByDeliveryPartnerId(Long deliveryPartnetId);
	
	List<Review> findByCustomerId(Long customerId);
	
	List<Review> findByReviewType(ReviewType type);
}
