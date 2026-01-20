package com.restaurantdelivery.mapper;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.DeliveryAssignmentRequest;
import com.restaurantdelivery.dto.response.DeliveryAssignmentResponse;
import com.restaurantdelivery.entity.DeliveryAssignment;
import com.restaurantdelivery.entity.Order;
import com.restaurantdelivery.entity.User;

@Component
public class DeliveryAssignmentMapper {
	
	private final AddressMapper addressMapper;

	public DeliveryAssignmentMapper(AddressMapper addressMapper) {
		super();
		this.addressMapper = addressMapper;
	}

	public DeliveryAssignment toDeliveryAssignmentEntity(DeliveryAssignmentRequest request, Order order,
			User deliveryPartner, User customer) {
		
		DeliveryAssignment deliveryAssignment = new DeliveryAssignment();
		deliveryAssignment.setOrder(order);
		deliveryAssignment.setDeliveryPartner(deliveryPartner);
		deliveryAssignment.setCustomer(customer);
		
		return deliveryAssignment;
	}
	
	public DeliveryAssignmentResponse toDeliveryAssignmentResponse(DeliveryAssignment deliveryAssignment) {
		DeliveryAssignmentResponse response = new DeliveryAssignmentResponse();
		response.setId(deliveryAssignment.getId());
		response.setOrderId(deliveryAssignment.getOrder().getId());
		response.setDeliveryPartnerId(deliveryAssignment.getDeliveryPartner().getId());
		response.setDeliveryPartnerName(deliveryAssignment.getDeliveryPartner().getFirstName()+" "+deliveryAssignment.getDeliveryPartner().getLastName());
		response.setDeliveryAddress(addressMapper.toAddressResponse(deliveryAssignment.getOrder().getDeliveryAddress()));
		response.setDeliveryPartnerPhone(deliveryAssignment.getDeliveryPartner().getPhoneNumber());
		response.setCustomerName(deliveryAssignment.getCustomer().getFirstName()+ " "+deliveryAssignment.getCustomer().getLastName());
		response.setCustomerPhone(deliveryAssignment.getCustomer().getPhoneNumber());
		response.setStatus(deliveryAssignment.getStatus());
		response.setTotalAmount(deliveryAssignment.getOrder().getTotalAmount());
		response.setDeliveryFee(deliveryAssignment.getOrder().getDeliveryFee());
		response.setAssignedAt(deliveryAssignment.getAssignedAt());
		return response;
		
	}
	
}
