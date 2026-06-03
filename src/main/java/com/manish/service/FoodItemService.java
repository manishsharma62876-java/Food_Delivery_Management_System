package com.manish.service;

import java.util.List;

import com.manish.DTO.Request.FoodItemRequest;
import com.manish.DTO.Response.FoodItemResponse;

public interface FoodItemService {

	FoodItemResponse saveFoodItem(FoodItemRequest request);
	
	List<FoodItemResponse>  getAllFoodItems();
	
	FoodItemResponse getFoodItemById(Long id);
	
	List<FoodItemResponse>  getFoodItemByRestuarant(Long restaurantId);
	
	FoodItemResponse updateFoodItem(Long id , FoodItemRequest request);
	
	void deletefoodItem(Long id);
	
	
}
