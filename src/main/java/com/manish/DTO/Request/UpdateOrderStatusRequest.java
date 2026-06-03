package com.manish.DTO.Request;

import com.manish.entity.OrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderStatusRequest {

	@NotNull(message = "Order status is required")
	private OrderStatus status;

}
