package com.manish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manish.entity.Restaurant;
import java.util.List;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

	List<Restaurant> findByCuisineType(String cuisineType);
	
	List<Restaurant> findByName(String name);
	
	
}
