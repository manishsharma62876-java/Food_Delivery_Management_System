package com.manish.DTO.Request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FoodItemRequest {

	@NotBlank(message = "food item is required")
	private String name;

	private String description;

	@NotNull(message = "price is required")
	private BigDecimal price;

	@NotBlank(message = "category is required")
	private String category;

	private boolean isAvailable = true;

	@NotNull(message = "Restaurant ID is required")
	private Long restaurantId;

}
