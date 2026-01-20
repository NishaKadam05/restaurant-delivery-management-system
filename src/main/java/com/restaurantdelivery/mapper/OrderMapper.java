package com.restaurantdelivery.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.OrderRequest;
import com.restaurantdelivery.dto.response.OrderItemResponse;
import com.restaurantdelivery.dto.response.OrderResponse;
import com.restaurantdelivery.dto.response.PlaceOrderResponse;
import com.restaurantdelivery.entity.CustomerAddress;
import com.restaurantdelivery.entity.DeliveryAssignment;
import com.restaurantdelivery.entity.Offer;
import com.restaurantdelivery.entity.Order;
import com.restaurantdelivery.entity.OrderItem;
import com.restaurantdelivery.entity.User;

@Component
public class OrderMapper {
	
	private final OrderItemMapper orderItemMapper;
	private final AddressMapper addressMapper;

	public OrderMapper(OrderItemMapper orderItemMapper, AddressMapper addressMapper) {
		super();
		this.orderItemMapper = orderItemMapper;
		this.addressMapper = addressMapper;
	}

	public Order toOrderEntity(OrderRequest request, User customer,CustomerAddress address, Offer offer) {
		Order order = new Order();
		order.setCustomer(customer);
		order.setOrderType(request.getOrderType());
		order.setDeliveryAddress(address);
		order.setOffer(offer);
		order.setSpecialInstructions(request.getSpecialInstructions());
		order.setPaymentMethod(request.getPaymentMethod());
		return order;
	}
	
	public OrderResponse toOrderResponse(Order order) {
		OrderResponse response = new OrderResponse();
		response.setId(order.getId());
		response.setCustomerId(order.getCustomer().getId());
		response.setCustomerName(order.getCustomer().getFirstName()+" "+order.getCustomer().getLastName());
		response.setCustomerPhone(order.getCustomer().getPhoneNumber());
		response.setDeliveryAddress(addressMapper.toAddressResponse(order.getDeliveryAddress()));
		response.setDeliveryPartnerId(order.getDeliveryAssignment().getDeliveryPartner().getId());
		response.setDeliveryPartnerName(order.getDeliveryAssignment().getDeliveryPartner().getFirstName()+" "+order.getDeliveryAssignment().getDeliveryPartner().getLastName());
		response.setDeliveryPartnerPhone(order.getDeliveryAssignment().getDeliveryPartner().getPhoneNumber());
		response.setOrderType(order.getOrderType());
		response.setOrderStatus(order.getOrderStatus());
		response.setOfferCode(order.getOffer().getCode());
		response.setSpecialInstructions(order.getSpecialInstructions());
		response.setDeliveryFee(order.getDeliveryFee());
		response.setSubtotal(order.getSubtotal());
		response.setTotalAmount(order.getTotalAmount());
		response.setDiscountAmount(order.getDiscountAmount());
		response.setCreatedAt(order.getCreatedAt());
		response.setUpdatedAt(order.getUpdatedAt());
		
		return response;
	}
	
	public PlaceOrderResponse toPlaceOrderResponse(Order order) {
		
		PlaceOrderResponse response = new PlaceOrderResponse();
		response.setId(order.getId());
		response.setCustomerId(order.getCustomer().getId());
		response.setCustomerName(order.getCustomer().getFirstName()+" "+order.getCustomer().getLastName());
		response.setCustomerPhone(order.getCustomer().getPhoneNumber());
		response.setDeliveryAddress(addressMapper.toAddressResponse(order.getDeliveryAddress()));
		response.setOrderType(order.getOrderType());
		response.setOrderStatus(order.getOrderStatus());
		response.setOfferCode(order.getOffer().getCode());
		response.setSpecialInstructions(order.getSpecialInstructions());
		response.setDeliveryFee(order.getDeliveryFee());
		response.setSubtotal(order.getSubtotal());
		response.setTotalAmount(order.getTotalAmount());
		response.setDiscountAmount(order.getDiscountAmount());
		response.setCreatedAt(order.getCreatedAt());
		response.setUpdatedAt(order.getUpdatedAt());
		
		List<OrderItem> items = order.getOrderItems();
		List<OrderItemResponse> itemList = new ArrayList<>();
		for(OrderItem item: items)
			itemList.add(orderItemMapper.toOrderItemResponse(item));
			
		response.setItems(itemList);
		
		return response;
	}
	
}
