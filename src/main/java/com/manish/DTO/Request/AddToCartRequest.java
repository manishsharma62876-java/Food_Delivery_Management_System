package com.manish.DTO.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartRequest {

	@NotNull(message = "Food item ID is required")
	private Long foodItemId;

	@Min(value = 1, message = "quantity must be at least 1")
	private int quantity = 1;
}
