package com.restaurantdelivery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurantdelivery.entity.MenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
	
	List<MenuItem> findByAvailableTrue();
	
	List<MenuItem> findByCategory_IdAndAvailableTrue(Long categoryId);
	
	Optional<MenuItem> findByName(String name);
	
	List<MenuItem> findByIdAndAvailableTrue(Long menuItemId);
	
	boolean existsByNameAndCategoryId(String name, Long categoryId);
	
	List<MenuItem> findByVegTrue();
	
	List<MenuItem> findByVegFalse();
	

}
