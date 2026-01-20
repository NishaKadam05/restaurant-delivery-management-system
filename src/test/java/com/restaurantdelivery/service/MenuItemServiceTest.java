package com.restaurantdelivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.restaurantdelivery.dto.request.MenuItemRequest;
import com.restaurantdelivery.dto.response.MenuItemResponse;
import com.restaurantdelivery.entity.Category;
import com.restaurantdelivery.entity.MenuItem;
import com.restaurantdelivery.exception.BadRequestException;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.mapper.MenuItemMapper;
import com.restaurantdelivery.repository.CategoryRepository;
import com.restaurantdelivery.repository.MenuItemRepository;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {
 
    @Mock
    private MenuItemRepository menuItemRepository;
 
    @Mock
    private CategoryRepository categoryRepository;
 
    @Mock
    private MenuItemMapper menuItemMapper;
 
    @InjectMocks
    private MenuItemService menuItemService;
    
    @Test
    void createMenuItem_success() {
     
        MenuItemRequest request = new MenuItemRequest();
        request.setName("Chicken Biryani");
        request.setCategoryId(1L);
     
        Category category = new Category();
        MenuItem menuItem = new MenuItem();
        MenuItemResponse response = new MenuItemResponse();
     
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));
        when(menuItemRepository.existsByNameAndCategoryId(
                request.getName(), request.getCategoryId()))
                .thenReturn(false);
        when(menuItemMapper.toMenuItemEntity(request, category))
                .thenReturn(menuItem);
        when(menuItemRepository.save(menuItem))
                .thenReturn(menuItem);
        when(menuItemMapper.toMenuItemResponse(menuItem))
                .thenReturn(response);
     
        ResponseEntity<?> result = menuItemService.createMenuItem(request);
     
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
    
    @Test
    void getMenuItemById_success() {
     
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1L);
     
        when(menuItemRepository.findById(1L))
                .thenReturn(Optional.of(menuItem));
        when(menuItemMapper.toMenuItemResponse(menuItem))
                .thenReturn(new MenuItemResponse());
     
        ResponseEntity<?> response = menuItemService.getMenuItemById(1L);
     
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void getAvailableMenuItems_success() {
     
        List<MenuItem> menuItems = List.of(new MenuItem(), new MenuItem());
     
        when(menuItemRepository.findByAvailableTrue())
                .thenReturn(menuItems);
        when(menuItemMapper.toMenuItemResponse(any(MenuItem.class)))
                .thenReturn(new MenuItemResponse());
     
        ResponseEntity<?> response = menuItemService.getAvailableMenuItems();
     
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void createMenuItem_categoryNotFound() {
     
        MenuItemRequest request = new MenuItemRequest();
        request.setCategoryId(1L);
     
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());
     
        assertThrows(ResourceNotFoundException.class,
                () -> menuItemService.createMenuItem(request));
    }
    
    @Test
    void createMenuItem_menuItemAlreadyExists() {
     
        MenuItemRequest request = new MenuItemRequest();
        request.setName("Burger");
        request.setCategoryId(1L);
     
        Category category = new Category();
     
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));
        when(menuItemRepository.existsByNameAndCategoryId(
                request.getName(), request.getCategoryId()))
                .thenReturn(true);
     
        assertThrows(BadRequestException.class,
                () -> menuItemService.createMenuItem(request));
    }
    
    @Test
    void getMenuItemByName_notFound() {
     
        when(menuItemRepository.findByName("Pizza"))
                .thenReturn(Optional.empty());
     
        assertThrows(ResourceNotFoundException.class,
                () -> menuItemService.getMenuItemByName("Pizza"));
    }
}
