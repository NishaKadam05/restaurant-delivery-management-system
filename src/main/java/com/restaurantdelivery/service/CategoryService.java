package com.restaurantdelivery.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.dto.request.CategoryRequest;
import com.restaurantdelivery.dto.response.CategoryResponse;
import com.restaurantdelivery.entity.Category;
import com.restaurantdelivery.entity.Restaurant;
import com.restaurantdelivery.exception.BadRequestException;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.mapper.CategoryMapper;
import com.restaurantdelivery.repository.CategoryRepository;
import com.restaurantdelivery.repository.RestaurantRepository;

@Service
public class CategoryService {
	
	Logger logger = LoggerFactory.getLogger(CategoryService.class);

	private final CategoryRepository categoryRepository;
	private final RestaurantRepository restaurantRepository;
	private final CategoryMapper categoryMapper;
	
	public CategoryService(CategoryRepository categoryRepository, RestaurantRepository restaurantRepository,
			CategoryMapper categoryMapper) {
		super();
		this.categoryRepository = categoryRepository;
		this.restaurantRepository = restaurantRepository;
		this.categoryMapper = categoryMapper;
	}

	public ResponseEntity<?> createCategory(CategoryRequest request){
		logger.info("Creating a category");
		Restaurant restaurant = restaurantRepository.findByOpenTrue()
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Found"));
		
		if(categoryRepository.existsByName(request.getName())) {
			logger.warn("category already exists");
			throw new BadRequestException("Category already exixts!");
		}
		
		Category category = categoryMapper.toCategoryEntity(request);
		CategoryResponse response = categoryMapper.toCategoryResponse(categoryRepository.save(category));
		
		logger.info("Created a category");
		return new ResponseEntity<> (response, HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> getAllCategories(){
		logger.info("Getting all categories");
		Restaurant restaurant = restaurantRepository.findByOpenTrue()
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Open"));
		
		List<Category> categories = categoryRepository.findAll();

	    if (categories.isEmpty()) {
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body("No Categories Found");
	    }

	    List<CategoryResponse> categoryList = categories.stream()
	            .map(categoryMapper::toCategoryResponse)
	            .toList(); 

	    return ResponseEntity.ok(categoryList);
		
	}
	
	public ResponseEntity<?> getActiveCategories(){
		logger.info("Getting active categories");
		Restaurant restaurant = restaurantRepository.findByOpenTrue()
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Open"));
		
		List<CategoryResponse> categoryList = categoryRepository.findByActiveTrue()
				.stream()
				 .map(categoryMapper::toCategoryResponse)
				 .toList(); 
		
		return ResponseEntity.ok(categoryList);
	}
	
	public ResponseEntity<?> getCategoryById(Long id){
		logger.info("Getting category by id "+id);
		Restaurant restaurant = restaurantRepository.findByOpenTrue()
				.orElseThrow(() -> new ResourceNotFoundException("Restaurant Not Open"));
		
		Category category = categoryRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Category Not Found with the Id "+id));
		
		return ResponseEntity.ok(categoryMapper.toCategoryResponse(category));
	}
	
	public ResponseEntity<?> updateCategory(CategoryRequest request, Long id){
		Category category = categoryRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Category Not Found with the Id "+id));
		
		categoryMapper.updateEntity(request, category);
		CategoryResponse response = categoryMapper.toCategoryResponse(categoryRepository.save(category));
		
		return ResponseEntity.ok(response);
	}
}
