package com.restaurantdelivery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurantdelivery.entity.User;
import com.restaurantdelivery.enums.UserRole;
import com.restaurantdelivery.enums.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	List<User> findByStatus(UserStatus status);
	
	List<User> findByRole(UserRole role);
	
}
