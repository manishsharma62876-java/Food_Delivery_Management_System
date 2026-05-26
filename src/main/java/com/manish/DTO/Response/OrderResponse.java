package com.manish.DTO.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.manish.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {

	private Long id;
	private Long userId;
	private String userName;
	private Long restaurantId;
	private String restaurantName;
	private OrderStatus status;
	private BigDecimal totalAmount;
	private String deliveryAddress;
	private String notes;
	private List<OrderItemResponse> orderItems;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
