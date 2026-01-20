package com.restaurantdelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantdelivery.dto.request.CategoryRequest;
import com.restaurantdelivery.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_STAFF')")
	@PostMapping
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest request){
		return categoryService.createCategory(request);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllCategories(){
		return categoryService.getAllCategories();
	}
	
	@GetMapping("/active")
	public ResponseEntity<?> getActiveCategories(){
		return categoryService.getActiveCategories();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Long id){
		return categoryService.getCategoryById(id);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_STAFF')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@RequestBody CategoryRequest request, @PathVariable Long id){
		return categoryService.updateCategory(request, id);
	}
}
