package com.restaurantdelivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantdelivery.dto.request.MenuItemRequest;
import com.restaurantdelivery.service.MenuItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

	@Autowired
	private MenuItemService menuItemService;
	
	@PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_STAFF')")
	@PostMapping
	public ResponseEntity<?> createMenuItem(@Valid @RequestBody MenuItemRequest request){
		return menuItemService.createMenuItem(request);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllMenuItems(){
		return menuItemService.getAllMenuItems();
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getMenuItemById(@PathVariable Long id){
		return menuItemService.getMenuItemById(id);
	}
	
	@GetMapping("/name")
	public ResponseEntity<?> getMenuItemByName(@RequestParam String name){
		return menuItemService.getMenuItemByName(name);
	}
	
	@GetMapping("/available")
	public ResponseEntity<?> getAvailableMenuItems(){
		return menuItemService.getAvailableMenuItems();
	}
	
	@GetMapping("/availableAndCategory/{categoryId}")
	public ResponseEntity<?> getAvailableMenuItemsByCategoryId(@PathVariable Long categoryId){
		return menuItemService.getAvailableMenuItemsByCategoryId(categoryId);
	}
	
	@GetMapping("/available/{id}")
	public ResponseEntity<?> getAvailableMenuItemsById(@PathVariable Long id){
		return menuItemService.getAvailableMenuItemsById(id);
	}
	
	@GetMapping("/veg")
	public ResponseEntity<?> getVegMenuItems(){
		return menuItemService.getMenuItemByIsVeg();
	}
	
	@GetMapping("/non-veg")
	public ResponseEntity<?> getNonVegMenuItems(){
		return menuItemService.getMenuItemByIsNonVeg();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_STAFF')")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateMenuItem(@Valid @RequestBody MenuItemRequest request,@PathVariable Long id){
		return menuItemService.updateMenuItem(request, id);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_STAFF')")
	@PatchMapping("/updateAvailability/{id}/{available}")
	public ResponseEntity<?> updateAvailability(@PathVariable Long id, @RequestParam boolean available){
		return menuItemService.updateAvailability(id, available);
	}
	
	
}
