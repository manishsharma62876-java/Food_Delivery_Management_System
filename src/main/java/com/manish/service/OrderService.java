package com.manish.service;

import java.util.List;

import com.manish.DTO.Request.PlaceOrderRequest;
import com.manish.DTO.Request.UpdateOrderStatusRequest;
import com.manish.DTO.Response.OrderResponse;

public interface OrderService {

	OrderResponse  placeOrder(PlaceOrderRequest request);
	
	OrderResponse getOrderById(Long orderId);
	
	List<OrderResponse> getOrdersByUserId(Long userId);
	
	OrderResponse updateOrderStatus(Long orderId , UpdateOrderStatusRequest request);
	
	OrderResponse cancelOrder(Long orderId);

}
