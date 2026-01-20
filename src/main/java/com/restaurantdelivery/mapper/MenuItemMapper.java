package com.restaurantdelivery.mapper;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.MenuItemRequest;
import com.restaurantdelivery.dto.response.MenuItemResponse;
import com.restaurantdelivery.entity.Category;
import com.restaurantdelivery.entity.MenuItem;

@Component
public class MenuItemMapper {

	public MenuItem toMenuItemEntity(MenuItemRequest request, Category category) {
		MenuItem menuItem = new MenuItem();
        menuItem.setCategory(category);
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setBasePrice(request.getBasePrice());
        menuItem.setAvailable(request.getAvailable());
        menuItem.setVeg(request.getVeg());
        menuItem.setPreparationTime(request.getPreparationTime());
        
        return menuItem;
	}
	
	public MenuItemResponse toMenuItemResponse(MenuItem menuItem) {
		MenuItemResponse response = new MenuItemResponse();
		response.setId(menuItem.getId());
		response.setCategoryId(menuItem.getCategory().getId());
		response.setCategoryName(menuItem.getCategory().getName());
		response.setName(menuItem.getName());
		response.setDescription(menuItem.getDescription());
		response.setBasePrice(menuItem.getBasePrice());
		response.setAvailable(menuItem.getAvailable());
		response.setVeg(menuItem.getVeg());
		response.setPreparationTime(menuItem.getPreparationTime());
		response.setAverageRating(menuItem.getAverageRating());
		response.setTotalReviews(menuItem.getTotalReviews());
		response.setCreatedAt(menuItem.getCreatedAt());
		response.setUpdatedAt(menuItem.getUpdatedAt());
		
		return response;
	}
	
	public void updateEntity(MenuItemRequest request, MenuItem menuItem, Category category) {
		menuItem.setCategory(category);
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setBasePrice(request.getBasePrice());
        menuItem.setAvailable(request.getAvailable());
        menuItem.setVeg(request.getVeg());
        menuItem.setPreparationTime(request.getPreparationTime());
	}
}
