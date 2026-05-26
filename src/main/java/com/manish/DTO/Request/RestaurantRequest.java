package com.manish.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RestaurantRequest {

	@NotBlank(message = "Restaurant name is required")
	private String name;

	@NotBlank(message = "Address is required")
	private String address;

	@NotBlank(message = "phone is required")
	private String phone;

	@NotBlank(message = "Cuisine type is required")
	private String cuisineType;

	
	private boolean isOpen = true;
}
