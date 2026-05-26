package com.manish.DTO.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodItemResponse {

	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	private String category;
	private boolean isAvailable;
	private Long restaurantId;
	private String restaurantName;
	private LocalDateTime createdAt;
	
}
