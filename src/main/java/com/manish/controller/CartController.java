package com.manish.controller;

import com.manish.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manish.DTO.Request.AddToCartRequest;
import com.manish.DTO.Request.UpdateCartItemRequest;
import com.manish.DTO.Response.ApiResponse;
import com.manish.DTO.Response.CartResponse;
import com.manish.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private  CartItemRepository cartItemRepository;
	
	@Autowired
	private CartService cartService;

	@PostMapping("/add")
	public ResponseEntity<ApiResponse<CartResponse>> addToCart(@RequestBody AddToCartRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Item added to cart succesfully..!", cartService.addToCart(request)));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<ApiResponse<CartResponse>> getCartByUserId(@PathVariable Long userId) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(ApiResponse.success("Cart fetched successfully......!", cartService.getCartByUserId(userId)));
	}

	@PutMapping("/item/{cartItemId}")
	public ResponseEntity<ApiResponse<CartResponse>> updateCartItem(@PathVariable Long cartItemId,
			@RequestBody UpdateCartItemRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse
				.success("Cart item updated successfullyy.....!", cartService.updateCartItem(cartItemId, request)));
	}

	@DeleteMapping("/item/{cartItemId}")
	public ResponseEntity<ApiResponse<CartResponse>> removeCartItem(@PathVariable Long cartItemId) {
		return ResponseEntity.status(HttpStatus.OK).body(
				ApiResponse.success("Cart Item removed successfully....!", cartService.removeCartItem(cartItemId)));
	}

	@DeleteMapping("/user/{userId}/clear")
	public ResponseEntity<ApiResponse<String>> clearCart(@PathVariable Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Cart cleared successfullyy", null));
	}
}
