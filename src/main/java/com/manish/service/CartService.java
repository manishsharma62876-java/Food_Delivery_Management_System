package com.manish.service;

import com.manish.DTO.Request.AddToCartRequest;
import com.manish.DTO.Request.UpdateCartItemRequest;
import com.manish.DTO.Response.CartResponse;

public interface CartService {

	CartResponse addToCart(AddToCartRequest request);
  
	CartResponse getCartByUserId(Long userId);
	
	CartResponse updateCartItem(Long cartItemId , UpdateCartItemRequest  request);
	
	CartResponse removeCartItem(Long cartItemId);
	
	void clearCart(Long userId);
	
	
}
