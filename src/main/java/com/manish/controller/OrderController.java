package com.manish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manish.DTO.Request.PlaceOrderRequest;
import com.manish.DTO.Request.UpdateOrderStatusRequest;
import com.manish.DTO.Response.ApiResponse;
import com.manish.DTO.Response.OrderResponse;
import com.manish.service.OrderService;

@RestController
@RequestMapping("/order/api")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/place")
	public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(@RequestBody PlaceOrderRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Order placed successfullyy", orderService.placeOrder(request)));
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Long orderId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(ApiResponse.success("Order fetched successfullyy....", orderService.getOrderById(orderId)));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByUserId(@PathVariable Long userId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(ApiResponse.success("Orders fetched successfullyy", orderService.getOrdersByUserId(userId)));
	}

	@PutMapping("/orderId/{status}")
	public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(@PathVariable Long orderId,
			@RequestBody UpdateOrderStatusRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Order status updated succesfully",
				orderService.updateOrderStatus(orderId, request)));
	}

	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(@PathVariable Long orderId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(ApiResponse.success("Order cancelled successfullyy", orderService.cancelOrder(orderId)));
	}
}
