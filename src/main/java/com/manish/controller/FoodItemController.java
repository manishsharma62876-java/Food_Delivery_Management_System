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

import com.manish.DTO.Request.FoodItemRequest;
import com.manish.DTO.Response.ApiResponse;
import com.manish.DTO.Response.FoodItemResponse;
import com.manish.service.FoodItemService;

@RestController
@RequestMapping("/api/foodItems")
public class FoodItemController {

	
	@Autowired
	private FoodItemService foodItemService;
	
	@PostMapping("/save")
	public ResponseEntity<ApiResponse<FoodItemResponse>>  saveFoodItem(@RequestBody FoodItemRequest request){
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("FoodItem saved successfully" , foodItemService.saveFoodItem(request)));
	}
	
	@GetMapping("getAllitems")
	public ResponseEntity<ApiResponse<List<FoodItemResponse>>>  getAllfoodItems(){
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("All food Items fetched successfullyy" , foodItemService.getAllFoodItems()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<FoodItemResponse>>  getfoodItembyId(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("food Item fetched successfulyy" , foodItemService.getFoodItemById(id)));
	}
	
	@GetMapping("/restuarant/{restaurantId}")
	public ResponseEntity<ApiResponse<List<FoodItemResponse>>> getfoodItemByRestuarant(@PathVariable Long restaurantId){
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Resturant food item fetched successfullyyy" ,foodItemService.getFoodItemByRestuarant(restaurantId) ));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<FoodItemResponse>>  updateFoodItem(@PathVariable  Long id , @RequestBody FoodItemRequest request){
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Food Item updated successfully" , foodItemService.updateFoodItem(id, request)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<FoodItemResponse>>  deletefoodItem(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("deleted succesfulyy" , null));
	}
}
