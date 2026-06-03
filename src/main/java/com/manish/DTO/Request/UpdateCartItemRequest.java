package com.manish.DTO.Request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateCartItemRequest {

	@Min(value = 1, message = "Quantity cannot be negative")
	private int quantity;
	
}
