package com.restaurantdelivery.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.dto.request.MenuItemRequest;
import com.restaurantdelivery.dto.response.MenuItemResponse;
import com.restaurantdelivery.entity.Category;
import com.restaurantdelivery.entity.MenuItem;
import com.restaurantdelivery.exception.BadRequestException;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.mapper.MenuItemMapper;
import com.restaurantdelivery.repository.CategoryRepository;
import com.restaurantdelivery.repository.MenuItemRepository;

@Service
public class MenuItemService {
	
	Logger logger = LoggerFactory.getLogger(MenuItemService.class);

	private final MenuItemRepository menuItemRepository;
	private final CategoryRepository categoryRepository;
	private final MenuItemMapper menuItemMapper;

	public MenuItemService(MenuItemRepository menuItemRepository, CategoryRepository categoryRepository,
			MenuItemMapper menuItemMapper) {
		super();
		this.menuItemRepository = menuItemRepository;
		this.categoryRepository = categoryRepository;
		this.menuItemMapper = menuItemMapper;
	}

	public ResponseEntity<?> createMenuItem(MenuItemRequest request) {
		
		logger.info("Creating a menu item");

		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));

		if (menuItemRepository.existsByNameAndCategoryId(request.getName(), request.getCategoryId())) {
			throw new BadRequestException("Menu Item already exists in the category");
		}

		MenuItem menuItem = menuItemMapper.toMenuItemEntity(request, category);
		MenuItemResponse response = menuItemMapper.toMenuItemResponse(menuItemRepository.save(menuItem));

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public ResponseEntity<?> getAllMenuItems() {
		
		logger.info("Getting all menu items");

		List<MenuItem> menuItems = menuItemRepository.findAll();

		if (menuItems.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No Menu Items Found");
		}

		List<MenuItemResponse> menuItemList = new ArrayList<>();
		for (MenuItem menuItem : menuItems) {
			menuItemList.add(menuItemMapper.toMenuItemResponse(menuItem));
		}

		return ResponseEntity.ok(menuItemList);

	}

	public ResponseEntity<?> getMenuItemById(Long id) {

		logger.info("Getting menu item by id "+id);
		MenuItem menuItem = menuItemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu Item Not Found with the Id " + id));

		return ResponseEntity.ok(menuItemMapper.toMenuItemResponse(menuItem));
	}

	public ResponseEntity<?> getMenuItemByName(String name) {
		logger.info("Getting menu item by name");
		MenuItem menuItem = menuItemRepository.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Menu Item Not Found with the name " + name));

		return ResponseEntity.ok(menuItemMapper.toMenuItemResponse(menuItem));
	}

	public ResponseEntity<?> getAvailableMenuItems() {
		
		logger.info("Getting available menu items");

		List<MenuItem> menuItems = menuItemRepository.findByAvailableTrue();
		if (menuItems.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No Menu Items Found");
		}

		List<MenuItemResponse> menuItemList = new ArrayList<>();
		for (MenuItem menuItem : menuItems) {
			menuItemList.add(menuItemMapper.toMenuItemResponse(menuItem));
		}

		return ResponseEntity.ok(menuItemList);
	}

	public ResponseEntity<?> getAvailableMenuItemsByCategoryId(Long categoryId) {

		List<MenuItem> menuItems = menuItemRepository.findByCategory_IdAndAvailableTrue(categoryId);
		if (menuItems.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No Menu Items Found");
		}

		List<MenuItemResponse> menuItemList = new ArrayList<>();
		for (MenuItem menuItem : menuItems) {
			menuItemList.add(menuItemMapper.toMenuItemResponse(menuItem));
		}

		return ResponseEntity.ok(menuItemList);
	}

	public ResponseEntity<?> getAvailableMenuItemsById(Long id) {

		List<MenuItem> menuItems = menuItemRepository.findByIdAndAvailableTrue(id);
		if (menuItems.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No Menu Items Found");
		}

		List<MenuItemResponse> menuItemList = new ArrayList<>();
		for (MenuItem menuItem : menuItems) {
			menuItemList.add(menuItemMapper.toMenuItemResponse(menuItem));
		}

		return ResponseEntity.ok(menuItemList);
	}

	public ResponseEntity<?> getMenuItemByIsVeg() {

		logger.info("Getting vegetarian menu items");
		List<MenuItem> menuItems = menuItemRepository.findByVegTrue();
		if (menuItems.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No Menu Items Found");
		}

		List<MenuItemResponse> menuItemList = new ArrayList<>();
		for (MenuItem menuItem : menuItems) {
			menuItemList.add(menuItemMapper.toMenuItemResponse(menuItem));
		}


		return ResponseEntity.ok(menuItemList);
	}
	
	public ResponseEntity<?> getMenuItemByIsNonVeg() {

		List<MenuItem> menuItems = menuItemRepository.findByVegFalse();
		if (menuItems.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body("No Menu Items Found");
		}

		List<MenuItemResponse> menuItemList = new ArrayList<>();
		for (MenuItem menuItem : menuItems) {
			menuItemList.add(menuItemMapper.toMenuItemResponse(menuItem));
		}


		return ResponseEntity.ok(menuItemList);
	}

	public ResponseEntity<?> updateMenuItem(MenuItemRequest request, Long id) {
		
		logger.info("Updating menu item");
		MenuItem menuItem = menuItemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu Item Not Found with the Id " + id));

		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));

		menuItemMapper.updateEntity(request, menuItem, category);
		menuItemRepository.save(menuItem);

		return ResponseEntity.ok(menuItemMapper.toMenuItemResponse(menuItem));
	}

	public ResponseEntity<?> updateAvailability(Long id, boolean available) {

		logger.info("Updating the availability status of menu item");
		MenuItem menuItem = menuItemRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Menu Item Not Found with the Id " + id));

		menuItem.setAvailable(available);
		menuItemRepository.save(menuItem);

		return ResponseEntity.ok("Menu Item availability successfully updated!");
	}

}
