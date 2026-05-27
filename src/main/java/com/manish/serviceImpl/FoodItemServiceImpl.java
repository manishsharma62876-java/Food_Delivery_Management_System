package com.manish.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manish.DTO.Request.FoodItemRequest;
import com.manish.DTO.Response.FoodItemResponse;
import com.manish.entity.FoodItem;
import com.manish.entity.Restaurant;
import com.manish.exception.ResourceNotFoundException;
import com.manish.repository.FoodItemRepository;
import com.manish.repository.RestaurantRepository;
import com.manish.service.FoodItemService;

@Service
public class FoodItemServiceImpl implements FoodItemService{

	
	@Autowired
	private FoodItemRepository foodItemRepository;
	
	@Autowired
	private RestaurantRepository restaurantrepository;
	
	@Override
	public FoodItemResponse saveFoodItem(FoodItemRequest request) {
		Restaurant resturant  = restaurantrepository.findById(request.getRestaurantId()).orElseThrow(() -> new ResourceNotFoundException("Restuarant not found::"));
		FoodItem foodItem = FoodItem.builder()
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.isAvailable(request.isAvailable())
				.restaurant(resturant)
				.build();
		
		FoodItem saved = foodItemRepository.save(foodItem);
		return toResponse(saved);
	}

	private FoodItemResponse toResponse(FoodItem foodItem) {
		return FoodItemResponse.builder()
				.id(foodItem.getId())
				.name(foodItem.getName())
				.description(foodItem.getDescription())
				.price(foodItem.getPrice())
				.isAvailable(foodItem.isAvailable())
				.restaurantName(foodItem.getRestaurant().getName())
				.build();
	}

	@Override
	public List<FoodItemResponse> getAllFoodItems() {
		List<FoodItem> items = foodItemRepository.findAll();
		return items.stream().map(item -> toResponse(item)).toList();
	}

	@Override
	public FoodItemResponse getFoodItemById(Long id) {
		FoodItem foodItem = foodItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("food item not found with::"+id));
		return toResponse(foodItem);
	}

	@Override
	public List<FoodItemResponse> getFoodItemByRestuarant(Long restaurantId) {
		List<FoodItem> byRestaurantId = foodItemRepository.findByRestaurantId(restaurantId);
		return byRestaurantId.stream().map(restuarant -> toResponse(restuarant)).toList();
	}

	@Override
	public FoodItemResponse updateFoodItem(Long id, FoodItemRequest request) {
		FoodItem foodItem = foodItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("fooditem not found with::"+id));
		Restaurant restaurant = restaurantrepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resturant not found::"+id));
		
		
		foodItem.setName(request.getName());
		foodItem.setDescription(request.getDescription());
		foodItem.setPrice(request.getPrice());
		foodItem.setCategory(request.getCategory());
		foodItem.setAvailable(request.isAvailable());
		foodItem.setRestaurant(restaurant);
		
		
		FoodItem updatedfoodItem = foodItemRepository.save(foodItem);
	
		return toResponse(updatedfoodItem);
	}

	@Override
	public void deletefoodItem(Long id) {
		FoodItem item = foodItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id not found with::"+id));
		
		foodItemRepository.delete(item);
	}
}
