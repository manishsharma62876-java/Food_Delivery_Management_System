package com.manish.DTO.Response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantResponse {

	private Long id;
	private String name;
	private String address;
	private String phone;
	private String cuisineType;
	private boolean isOpen;
	private LocalDateTime createdAt;
}
