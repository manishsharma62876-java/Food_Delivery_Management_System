package com.manish.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlaceOrderRequest {

	@NotNull(message = "User ID is required")
	private Long userId;

	@NotNull(message = "Reastaurant ID is required")
	private Long restaurantId;

	@NotBlank(message = "Delivery address is required")
	private String deliveryAddress;

	private String notes;
}
