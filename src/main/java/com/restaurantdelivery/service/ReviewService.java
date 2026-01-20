package com.restaurantdelivery.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.config.CurrentUserUtil;
import com.restaurantdelivery.dto.request.ReviewRequest;
import com.restaurantdelivery.dto.response.ReviewResponse;
import com.restaurantdelivery.entity.MenuItem;
import com.restaurantdelivery.entity.Order;
import com.restaurantdelivery.entity.Restaurant;
import com.restaurantdelivery.entity.Review;
import com.restaurantdelivery.entity.User;
import com.restaurantdelivery.enums.ReviewType;
import com.restaurantdelivery.exception.BadRequestException;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.mapper.ReviewMapper;
import com.restaurantdelivery.repository.MenuItemRepository;
import com.restaurantdelivery.repository.OrderRepository;
import com.restaurantdelivery.repository.RestaurantRepository;
import com.restaurantdelivery.repository.ReviewRepository;
import com.restaurantdelivery.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {
	
	Logger logger = LoggerFactory.getLogger(ReviewService.class);

	private final ReviewRepository reviewRepository;
	private final OrderRepository orderRepository;
	private final MenuItemRepository menuItemRepository;
	private final UserRepository userRepository;
	private final ReviewMapper reviewMapper;
	private final RestaurantRepository restaurantRepository;

	public ReviewService(ReviewRepository reviewRepository, OrderRepository orderRepository,
			MenuItemRepository menuItemRepository, UserRepository userRepository, ReviewMapper reviewMapper,
			RestaurantRepository restaurantRepository) {
		super();
		this.reviewRepository = reviewRepository;
		this.orderRepository = orderRepository;
		this.menuItemRepository = menuItemRepository;
		this.userRepository = userRepository;
		this.reviewMapper = reviewMapper;
		this.restaurantRepository = restaurantRepository;
	}

	@Transactional
	public ResponseEntity<?> createReview(ReviewRequest request) {

		logger.info("Creating a review");
		User user = getCurrentUser();
		
		Order order = orderRepository.findById(request.getOrderId())
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));

		Review review = reviewMapper.toReviewEntity(request, order);
		review.setCustomer(user);

		switch (request.getReviewType()) {

		case RESTAURANT -> handleRestaurantReview(request, review, user);

		case FOOD -> handleMenuItemReview(request, review);

		case DELIVERY -> handleDeliveryPartnerReview(request, review);

		default -> throw new BadRequestException("Invalid review type");
		}

		reviewRepository.save(review);
		ReviewResponse response = reviewMapper.toReviewResponse(review);
		return ResponseEntity.ok(response);
	}

	private void handleRestaurantReview(ReviewRequest request, Review review, User user) {

		if (request.getRestaurantId() == null) {
			throw new BadRequestException("Restaurant ID required for restaurant review");
		}

		if (reviewRepository.existsByRestaurantIdAndCustomerId(request.getRestaurantId(), user.getId())) {
			throw new BadRequestException("Restaurant already reviewed");
		}

		Restaurant restaurant = restaurantRepository.findById(request.getOrderId())
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

		int oldTotal = restaurant.getTotalReviews();
		double oldAverage = restaurant.getAverageRating();

		int newTotal = oldTotal + 1;
		double newAverage = ((oldAverage * oldTotal) + request.getRating()) / newTotal;

		restaurant.setAverageRating(newAverage);
		restaurant.setTotalReviews(newTotal);

		review.setRestaurant(restaurant);
	}

	private void handleMenuItemReview(ReviewRequest request, Review review) {

		if (request.getMenuItemId() == null) {
			throw new BadRequestException("Menu item ID required");
		}

		MenuItem item = menuItemRepository.findById(request.getMenuItemId())
				.orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

		int oldTotal = item.getTotalReviews();
		double oldAverage = item.getAverageRating();

		int newTotal = oldTotal + 1;
		double newAverage = ((oldAverage * oldTotal) + request.getRating()) / newTotal;

		item.setTotalReviews(newTotal);
		item.setAverageRating(newAverage);

		review.setMenuItem(item);
	}

	private void handleDeliveryPartnerReview(ReviewRequest request, Review review) {

		if (request.getDeliveryPartnerId() == null) {
			throw new BadRequestException("Delivery partner ID required");
		}

		User partner = userRepository.findById(request.getDeliveryPartnerId())
				.orElseThrow(() -> new ResourceNotFoundException("Delivery partner not found"));

		review.setDeliveryPartner(partner);
	}

	public ResponseEntity<?> getAllReviews() {

		List<Review> reviews = reviewRepository.findAll();

		if (reviews.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No reviews added");
		}

		List<ReviewResponse> reviewList = new ArrayList<>();
		for (Review review : reviews)
			reviewList.add(reviewMapper.toReviewResponse(review));

		return ResponseEntity.ok(reviewList);
	}

	public ResponseEntity<?> getReviewById(Long id) {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Review not found"));

		ReviewResponse response = reviewMapper.toReviewResponse(review);

		return ResponseEntity.ok(response);
	}

	
	public ResponseEntity<?> getReviewsByReviewType(ReviewType type) {

		List<Review> reviews = reviewRepository.findByReviewType(type);

		if (reviews.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No reviews found for "+ type);
		}

		List<ReviewResponse> reviewList = new ArrayList<>();
		for (Review review : reviews)
			reviewList.add(reviewMapper.toReviewResponse(review));

		return ResponseEntity.ok(reviewList);
	}
	
	public ResponseEntity<?> getReviewsByCustomerId(Long id) {

		List<Review> reviews = reviewRepository.findByCustomerId(id);

		if (reviews.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No reviews found for id"+ id);
		}

		List<ReviewResponse> reviewList = new ArrayList<>();
		for (Review review : reviews)
			reviewList.add(reviewMapper.toReviewResponse(review));

		return ResponseEntity.ok(reviewList);
	}

	public ResponseEntity<?> myReviews() {

		User user = getCurrentUser();

		List<Review> reviews = reviewRepository.findByCustomerId(user.getId());

		if (reviews.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No reviews added");
		}

		List<ReviewResponse> reviewList = new ArrayList<>();
		for (Review review : reviews)
			reviewList.add(reviewMapper.toReviewResponse(review));

		return ResponseEntity.ok(reviewList);
	}

	private User getCurrentUser() {
		return userRepository.findByEmail(CurrentUserUtil.getCurrentUserEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
	}
}
