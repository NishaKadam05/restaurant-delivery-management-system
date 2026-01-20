package com.restaurantdelivery.mapper;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.ReviewRequest;
import com.restaurantdelivery.dto.response.ReviewResponse;
import com.restaurantdelivery.entity.Order;
import com.restaurantdelivery.entity.Review;

@Component
public class ReviewMapper {
	 
    public  Review toReviewEntity(ReviewRequest request,Order order) {
 
        Review review = new Review();
        review.setOrder(order);
        review.setReviewType(request.getReviewType());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
 
        return review;
    }
 
    public ReviewResponse toReviewResponse(Review review) {
 
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setOrderId(review.getOrder().getId());
        response.setReviewType(review.getReviewType());
        response.setRating(review.getRating());
        response.setComment(review.getComment());
        response.setCustomerId(review.getCustomer().getId());
        response.setCustomerName(review.getCustomer().getFirstName()+" "+review.getCustomer().getLastName());
        response.setCreatedAt(review.getCreatedAt());
        response.setUpdatedAt(review.getUpdatedAt());
 
        switch(review.getReviewType()) {
        case FOOD -> {
        	response.setMenuItemId(review.getMenuItem().getId());
        	response.setMenuItemName(response.getMenuItemName());
        }
        
        case DELIVERY  ->{
        	response.setDeliveryPartnerId(review.getDeliveryPartner().getId());
        	response.setDeliveryPartnerName(review.getDeliveryPartner().getFirstName()+" "+review.getDeliveryPartner().getLastName());
        }
        
        case RESTAURANT -> {
        	response.setRestaurantId(review.getRestaurant().getId());
        }
        
        }
 
        return response;
    }
}
