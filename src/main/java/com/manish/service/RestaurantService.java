package com.manish.service;

import java.util.List;

import com.manish.DTO.Request.RestaurantRequest;
import com.manish.DTO.Response.RestaurantResponse;

public interface RestaurantService {

	RestaurantResponse createRestaurant(RestaurantRequest request);
	
	List<RestaurantResponse> getAllRestaurants ();
	
	RestaurantResponse getRestaurantById(Long id);
	
	RestaurantResponse updateRestaurant(Long id , RestaurantRequest request);
	
	void deleteRestaurant(Long id);
	
	List<RestaurantResponse> findRestaurantByCuisineType(String cuisineType);
	
	List<RestaurantResponse>  findRestaurantByname(String name);
	
	long CountRestaurant();
	
}
