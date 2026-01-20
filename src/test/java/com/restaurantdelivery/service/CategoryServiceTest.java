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

import com.restaurantdelivery.dto.request.CategoryRequest;
import com.restaurantdelivery.dto.response.CategoryResponse;
import com.restaurantdelivery.entity.Category;
import com.restaurantdelivery.entity.Restaurant;
import com.restaurantdelivery.exception.BadRequestException;
import com.restaurantdelivery.exception.ResourceNotFoundException;
import com.restaurantdelivery.mapper.CategoryMapper;
import com.restaurantdelivery.repository.CategoryRepository;
import com.restaurantdelivery.repository.RestaurantRepository;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
 
    @Mock
    private CategoryRepository categoryRepository;
 
    @Mock
    private RestaurantRepository restaurantRepository;
 
    @Mock
    private CategoryMapper categoryMapper;
 
    @InjectMocks
    private CategoryService categoryService;
    
   
    @Test
    void getCategoryById_success() {
     
        Restaurant restaurant = new Restaurant();
        restaurant.setOpen(true);
     
        Category category = new Category();
        category.setId(1L);
     
        when(restaurantRepository.findByOpenTrue())
                .thenReturn(Optional.of(restaurant));
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));
        when(categoryMapper.toCategoryResponse(category))
                .thenReturn(new CategoryResponse());
     
        ResponseEntity<?> response = categoryService.getCategoryById(1L);
     
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void createCategory_categoryAlreadyExists() {
     
        CategoryRequest request = new CategoryRequest();
        request.setName("Starters");
     
        Restaurant restaurant = new Restaurant();
        restaurant.setOpen(true);
     
        when(restaurantRepository.findByOpenTrue())
                .thenReturn(Optional.of(restaurant));
        when(categoryRepository.existsByName(request.getName()))
                .thenReturn(true);
     
        assertThrows(BadRequestException.class,
                () -> categoryService.createCategory(request));
    }
    
    @Test
    void createCategory_success() {
     
        CategoryRequest request = new CategoryRequest();
        request.setName("Starters");
     
        Restaurant restaurant = new Restaurant();
        restaurant.setOpen(true);
     
        Category category = new Category();
        CategoryResponse response = new CategoryResponse();
     
        when(restaurantRepository.findByOpenTrue())
                .thenReturn(Optional.of(restaurant));
        when(categoryRepository.existsByName(request.getName()))
                .thenReturn(false);
        when(categoryMapper.toCategoryEntity(request))
                .thenReturn(category);
        when(categoryRepository.save(category))
                .thenReturn(category);
        when(categoryMapper.toCategoryResponse(category))
                .thenReturn(response);
     
        ResponseEntity<?> result = categoryService.createCategory(request);
     
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
    
    @Test
    void getAllCategories_success() {
     
        Restaurant restaurant = new Restaurant();
        restaurant.setOpen(true);
     
        List<Category> categories = List.of(new Category(), new Category());
     
        when(restaurantRepository.findByOpenTrue())
                .thenReturn(Optional.of(restaurant));
        when(categoryRepository.findAll())
                .thenReturn(categories);
        when(categoryMapper.toCategoryResponse(any(Category.class)))
                .thenReturn(new CategoryResponse());
     
        ResponseEntity<?> response = categoryService.getAllCategories();
     
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    
    @Test
    void getAllCategories_restaurantNotOpen() {
     
        when(restaurantRepository.findByOpenTrue())
                .thenReturn(Optional.empty());
     
        assertThrows(ResourceNotFoundException.class,
                () -> categoryService.getAllCategories());
    }
    
    @Test
    void getCategoryById_categoryNotFound() {
     
        Restaurant restaurant = new Restaurant();
        restaurant.setOpen(true);
     
        when(restaurantRepository.findByOpenTrue())
                .thenReturn(Optional.of(restaurant));
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());
     
        assertThrows(ResourceNotFoundException.class,
                () -> categoryService.getCategoryById(1L));
    }
}
