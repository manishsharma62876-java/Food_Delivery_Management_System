package com.manish.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manish.DTO.Request.RestaurantRequest;
import com.manish.DTO.Response.RestaurantResponse;
import com.manish.entity.Restaurant;
import com.manish.exception.ResourceNotFoundException;
import com.manish.repository.RestaurantRepository;
import com.manish.service.RestaurantService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService{
	
	
	@Autowired
	private RestaurantRepository restaurantRepository;

	//DTO --->  ENTITY
	@Override
	public RestaurantResponse createRestaurant(RestaurantRequest request) {
		Restaurant restaurant = Restaurant.builder()
				.name(request.getName())
				.address(request.getAddress())
				.phone(request.getPhone())
				.cuisineType(request.getCuisineType())
				.build();
		
		Restaurant save = restaurantRepository.save(restaurant);
		
		
		return toResponse(save);
	}
	
	private RestaurantResponse toResponse(Restaurant restaurant) {
		return RestaurantResponse.builder()
                     .id(restaurant.getId())
                     .name(restaurant.getName())
                     .address(restaurant.getAddress())
                     .phone(restaurant.getPhone())
                     .cuisineType(restaurant.getCuisineType())
                     .isOpen(restaurant.isOpen())
                     .build();
	}

	@Override
	public List<RestaurantResponse> getAllRestaurants() {
		List<Restaurant> restaurants = restaurantRepository.findAll();
		return restaurants.stream().map(restaurant  -> toResponse(restaurant)).toList();
	}

	@Override
	public RestaurantResponse getRestaurantById(Long id) {
		Restaurant byId = restaurantRepository.findById(id).orElseThrow(()  -> new ResourceNotFoundException("id not found with::"+id));
		return toResponse(byId);
	}

	@Override
	public RestaurantResponse updateRestaurant(Long id, RestaurantRequest request) {
		Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id not found with::"+id));
		
		restaurant.setName(request.getName());
		restaurant.setAddress(request.getAddress());
		restaurant.setPhone(request.getPhone());
		restaurant.setCuisineType(request.getCuisineType());
		
		Restaurant save = restaurantRepository.save(restaurant);
		
		return toResponse(save);
	}

	@Override
	public void deleteRestaurant(Long id) {
		Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id not found with::"+id));
		 restaurantRepository.delete(restaurant);
	}

	@Override
	public List<RestaurantResponse> findRestaurantByCuisineType(String cuisineType) {
		List<Restaurant> byCuisineType = restaurantRepository.findByCuisineType(cuisineType);
		return byCuisineType.stream().map(restaurant -> toResponse(restaurant)).toList();
	}

	@Override
	public List<RestaurantResponse> findRestaurantByname(String name) {
		List<Restaurant> byName = restaurantRepository.findByName(name);
		return byName.stream().map(restaurant -> toResponse(restaurant)).toList();
	}

	@Override
	public long CountRestaurant() {
		return restaurantRepository.count();
	}
	

}
