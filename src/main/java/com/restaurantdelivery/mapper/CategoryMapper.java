package com.restaurantdelivery.mapper;

import org.springframework.stereotype.Component;

import com.restaurantdelivery.dto.request.CategoryRequest;
import com.restaurantdelivery.dto.response.CategoryResponse;
import com.restaurantdelivery.entity.Category;

@Component
public class CategoryMapper {

	public Category toCategoryEntity(CategoryRequest request) {
		Category category = new Category();
		category.setName(request.getName());
		category.setDescription(request.getDescription());
		category.setActive(request.getActive());
		
		return category;
	}
	
	public CategoryResponse toCategoryResponse(Category category) {
		CategoryResponse response = new CategoryResponse();
		response.setId(category.getId());
		response.setName(category.getName());
		response.setDescription(category.getDescription());
		response.setActive(category.getActive());
		response.setCreatedAt(category.getCreatedAt());
		response.setUpdatedAt(category.getUpdatedAt());
		
		return response;
	}
	
	public void updateEntity(CategoryRequest request, Category category) {
		
		category.setName(request.getName());
		category.setDescription(request.getDescription());
		category.setActive(request.getActive());
		
	}
}
