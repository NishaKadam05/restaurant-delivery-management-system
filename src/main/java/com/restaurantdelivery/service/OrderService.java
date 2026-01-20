package com.restaurantdelivery.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.config.CurrentUserUtil;
import com.restaurantdelivery.dto.request.CancelOrderRequest;
import com.restaurantdelivery.dto.request.OrderItemRequest;
import com.restaurantdelivery.dto.request.OrderRequest;
import com.restaurantdelivery.dto.response.CancelOrderResponse;
import com.restaurantdelivery.dto.response.OrderResponse;
import com.restaurantdelivery.dto.response.PlaceOrderResponse;
import com.restaurantdelivery.entity.CustomerAddress;
import com.restaurantdelivery.entity.MenuItem;
import com.restaurantdelivery.entity.Offer;
import com.restaurantdelivery.entity.Order;
import com.restaurantdelivery.entity.OrderItem;
import com.restaurantdelivery.entity.User;
import com.restaurantdelivery.enums.DeliveryStatus;
import com.restaurantdelivery.enums.DiscountType;
import com.restaurantdelivery.enums.OrderStatus;
import com.restaurantdelivery.enums.OrderType;
import com.restaurantdelivery.enums.PaymentStatus;
import com.restaurantdelivery.exception.BadRequestException;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.mapper.CancelOrderMapper;
import com.restaurantdelivery.mapper.OrderItemMapper;
import com.restaurantdelivery.mapper.OrderMapper;
import com.restaurantdelivery.repository.AddressRepository;
import com.restaurantdelivery.repository.DeliveryRepository;
import com.restaurantdelivery.repository.MenuItemRepository;
import com.restaurantdelivery.repository.OfferRepository;
import com.restaurantdelivery.repository.OrderRepository;
import com.restaurantdelivery.repository.UserRepository;

@Service
public class OrderService {
	
	Logger logger = LoggerFactory.getLogger(OrderService.class);

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final AddressRepository addressRepository;
	private final MenuItemRepository menuItemRepository;
	private final OfferRepository offerRepository;
	private final OrderMapper orderMapper;
	private final OrderItemMapper orderItemMapper;
	private final PaymentService paymentService;
	private final DeliveryRepository deliveryRepository;
	private final CancelOrderMapper cancelOrderMapper;
	
	public OrderService(OrderRepository orderRepository, UserRepository userRepository,
			AddressRepository addressRepository, MenuItemRepository menuItemRepository, OfferRepository offerRepository,
			OrderMapper orderMapper,OrderItemMapper orderItemMapper,PaymentService paymentService,
			DeliveryRepository deliveryRepository,CancelOrderMapper cancelOrderMapper) {
		super();
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
		this.menuItemRepository = menuItemRepository;
		this.offerRepository = offerRepository;
		this.orderMapper = orderMapper;
		this.orderItemMapper = orderItemMapper;
		this.paymentService = paymentService;
		this.deliveryRepository = deliveryRepository;
		this.cancelOrderMapper = cancelOrderMapper;
	}
	
	public ResponseEntity<?> placeOrder(OrderRequest request){
		
		logger.info("Placing an order");
		
		User customer = userRepository.findByEmail(CurrentUserUtil.getCurrentUserEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		CustomerAddress address = null;
		
		if(request.getOrderType()== OrderType.DELIVERY) {
			address = addressRepository.findByIdAndUserId(request.getDeliveryAddressId(), customer.getId())
					.orElseThrow(() -> new BadRequestException("Invalid Address"));
		}
		
		Offer offer = offerRepository.findByCodeAndActiveTrue(request.getOfferCode())
				.orElseThrow(() -> new BadRequestException("Invalid or inactive offer code"));
		
		List<OrderItem> orderItems = new ArrayList<>();
		double subTotal = 0.0;
		
		Order order = orderMapper.toOrderEntity(request, customer, address, offer);
		
		for(OrderItemRequest itemReq : request.getItems()) {
			
			MenuItem menuItem = menuItemRepository.findById(itemReq.getMenuItemId())
					.orElseThrow(() -> new ResourceNotFoundException("Menu Item not found"));
			
			OrderItem orderItem = orderItemMapper.toOrderItemEntity(itemReq,order, menuItem);
			
			double itemPrice = menuItem.getBasePrice()*itemReq.getQuantity();
			orderItem.setUnitPrice(itemPrice);
			
			subTotal += itemPrice;
			order.addOrderItem(orderItem);
		}
		
		order.setSubtotal(subTotal);
		
		double discountAmount = 0;
		
		if(request.getOfferCode() != null && !request.getOfferCode().isBlank()) {
			
			if(offer.getValidUntil().isBefore(LocalDateTime.now())) {
				throw new BadRequestException("Offer has expired");
			}
			
			if(offer.getDiscountType()==DiscountType.PERCENTAGE) {
				discountAmount = (subTotal * offer.getDiscountValue())/100;
			}
		}
		
		double deliveryFee = 30;
		order.setDeliveryFee(deliveryFee);
		order.setDiscountAmount(discountAmount);
		order.setTotalAmount((subTotal+deliveryFee) - discountAmount);
		order.setOrderStatus(OrderStatus.PLACED);
		Order savedOrder = orderRepository.save(order);
		PlaceOrderResponse response= orderMapper.toPlaceOrderResponse(savedOrder);
		
		logger.info("Order placed successfully");
		return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<?> getAllOrders(){
		List<Order> orders = orderRepository.findAll();

	    if (orders.isEmpty()) {
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body("No Orders Found");
	    }

	    List<OrderResponse> orderList = new ArrayList<>();
	    for(Order order: orders)
	    	orderList.add(orderMapper.toOrderResponse(order));	

	    return ResponseEntity.ok(orderList);
	}
	
	public ResponseEntity<?> getOrderById(Long id){
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
		
		OrderResponse response = orderMapper.toOrderResponse(order);
	    return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<?> getOrderByCustomerId(Long customerId){
		User customer = userRepository.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		
		List<Order> orders = orderRepository.findByCustomerId(customerId);
		if (orders.isEmpty()) {
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body("No Orders Found");
	    }

	    List<OrderResponse> orderList = new ArrayList<>();
	    for(Order order: orders)
	    	orderList.add(orderMapper.toOrderResponse(order));	

	    return ResponseEntity.ok(orderList);
	}
	
	public ResponseEntity<?> getOrderByStatus(OrderStatus status){
		
		List<Order> orders = orderRepository.findByOrderStatus(status);
		if (orders.isEmpty()) {
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body("No Orders Found");
	    }

	    List<OrderResponse> orderList = new ArrayList<>();
	    for(Order order: orders)
	    	orderList.add(orderMapper.toOrderResponse(order));	

	    return ResponseEntity.ok(orderList);
	}
	
	public ResponseEntity<?> getOrderByType(OrderType type){
			
			List<Order> orders = orderRepository.findByOrderType(type);
			if (orders.isEmpty()) {
		        return ResponseEntity
		                .status(HttpStatus.OK)
		                .body("No Orders Found");
		    }
	
		    List<OrderResponse> orderList = new ArrayList<>();
		    for(Order order: orders)
		    	orderList.add(orderMapper.toOrderResponse(order));	
	
		    return ResponseEntity.ok(orderList);
		}
	
	public ResponseEntity<?> getOrderByDeliveryPartnerId(Long DeliveryPartnerId){
			
			List<Order> orders = orderRepository.findByDeliveryPartnerId(DeliveryPartnerId);
			if (orders.isEmpty()) {
		        return ResponseEntity
		                .status(HttpStatus.OK)
		                .body("No Orders Found");
		    }
	
		    List<OrderResponse> orderList = new ArrayList<>();
		    for(Order order: orders)
		    	orderList.add(orderMapper.toOrderResponse(order));	
	
		    return ResponseEntity.ok(orderList);
		}
	
	public ResponseEntity<?> cancelOrder(CancelOrderRequest request, Authentication authentication){

		Order order = orderRepository.findById(request.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Order not found"));
		
		validateCancellation(order, authentication);
		
		deliveryRepository.findByOrderId(order.getId())
				.ifPresent(delivery -> delivery.setStatus(DeliveryStatus.CANCELLED));
		
		cancelOrderMapper.updateEntity(request,order);
		Order savedOrder = orderRepository.save(order);
		CancelOrderResponse response = cancelOrderMapper.toCancelOrderResponse(savedOrder);
		
		return ResponseEntity.ok(response);
	}
	
	private void validateCancellation(Order order, Authentication auth) {
		
		boolean isAdmin = auth.getAuthorities()
				.stream()
				.anyMatch(a -> a.getAuthority().equals("ADMIN"));
		
		if(!isAdmin && (order.getOrderStatus()== OrderStatus.OUT_FOR_DELIVERY ||
				order.getOrderStatus() == OrderStatus.DELIVERED)) {
			throw new BadRequestException("Order cannot be cancelled at this stage");
		}
	}
}
