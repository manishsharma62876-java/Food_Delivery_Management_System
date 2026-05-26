package com.manish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manish.DTO.Request.RestaurantRequest;
import com.manish.DTO.Response.ApiResponse;
import com.manish.DTO.Response.RestaurantResponse;
import com.manish.service.RestaurantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	@PostMapping("/save")
	public ResponseEntity<ApiResponse<RestaurantResponse>> saveRestaurant(
			@Valid @RequestBody RestaurantRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
				ApiResponse.success("Restaurant create successfully", restaurantService.createRestaurant(request)));
	}

	@GetMapping("/AllRestaurants")
	public ResponseEntity<ApiResponse<List<RestaurantResponse>>> getAllRestaurants() {
		return ResponseEntity.status(HttpStatus.OK).body(
				ApiResponse.success("All restaurants fetched successfullyy", restaurantService.getAllRestaurants()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<RestaurantResponse>> getRestaurantById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(ApiResponse.success("Restaurant fecthed sucessfullyyy", restaurantService.getRestaurantById(id)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<RestaurantResponse>> updateRestaurant(@PathVariable Long id,
			@RequestBody RestaurantRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Restarant updated successfullyy",
				restaurantService.updateRestaurant(id, request)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<RestaurantResponse>> deleteRestaurant(@PathVariable Long id) {
		restaurantService.deleteRestaurant(id);
		return ResponseEntity.ok(ApiResponse.success("Restaurant deleted successfullyy", null));
	}
	
	@GetMapping("/cuisine/{cuisineType}")
	public ResponseEntity<ApiResponse<List<RestaurantResponse>>>  findRestaurantByCuisineType(@PathVariable String  cuisineType){
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Restaurant fetched successfully" ,restaurantService.findRestaurantByCuisineType(cuisineType)));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<ApiResponse<List<RestaurantResponse>>> findRestaurantByName(@PathVariable String name){
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Restaurant data fteched successfullyyy" , restaurantService.findRestaurantByname(name)));
	}
	
	@GetMapping("/count")
	public ResponseEntity<ApiResponse<Long>>  countRestaurants(){
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Total restaurant count successfully......!!!!" , restaurantService.CountRestaurant()));
	}
}
