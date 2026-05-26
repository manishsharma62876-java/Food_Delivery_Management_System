package com.manish.DTO.Response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartResponse {

	private Long id;
	private Long userId;
	private String userName;
	private List<CartItemResponse> items;
	private BigDecimal totalPrice;
	private int totalItems;
	
}
