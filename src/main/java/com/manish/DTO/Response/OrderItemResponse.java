package com.manish.DTO.Response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {

	private Long id;
	private Long foodItemId;
	private String foodItemName;
	private BigDecimal unitPrice;
	private int quantity;
	private BigDecimal subtotal;

}
