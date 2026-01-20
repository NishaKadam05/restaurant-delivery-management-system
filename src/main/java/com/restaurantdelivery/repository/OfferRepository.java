package com.restaurantdelivery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurantdelivery.entity.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

	Optional<Offer> findByCodeAndActiveTrue(String code);
	
	boolean existsByCodeIgnoreCase(String code);
	
	List<Offer> findByActiveTrue();
}
