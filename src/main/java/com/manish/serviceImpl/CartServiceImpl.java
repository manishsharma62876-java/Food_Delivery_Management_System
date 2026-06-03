package com.manish.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manish.DTO.Request.AddToCartRequest;
import com.manish.DTO.Request.UpdateCartItemRequest;
import com.manish.DTO.Response.CartItemResponse;
import com.manish.DTO.Response.CartResponse;
import com.manish.entity.Cart;
import com.manish.entity.CartItem;
import com.manish.entity.FoodItem;
import com.manish.entity.User;
import com.manish.exception.ResourceNotFoundException;
import com.manish.repository.CartItemRepository;
import com.manish.repository.CartRepository;
import com.manish.repository.FoodItemRepository;
import com.manish.repository.UserRepository;
import com.manish.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FoodItemRepository foodItemRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public CartResponse addToCart(
	        AddToCartRequest request) {

	    User user =
	            userRepository.findById(
	                    request.getUserId()
	            ).orElseThrow(
	                    () -> new ResourceNotFoundException(
	                            "User not found with id : "
	                                    + request.getUserId()
	                    )
	            );

	    FoodItem foodItem =
	            foodItemRepository.findById(
	                    request.getFoodItemId()
	            ).orElseThrow(
	                    () -> new ResourceNotFoundException(
	                            "Food item not found with id : "
	                                    + request.getFoodItemId()
	                    )
	            );

	    Cart cart =
	            cartRepository.findByUserId(
	                    user.getId()
	            ).orElseGet(() -> {

	                Cart newCart =
	                        Cart.builder()
	                                .user(user)
	                                .build();

	                return cartRepository.save(newCart);
	            });

	    CartItem existingCartItem =
	            cartItemRepository
	                    .findByCartIdAndFoodItemId(
	                            cart.getId(),
	                            foodItem.getId()
	                    );

	    if (existingCartItem != null) {

	        existingCartItem.setQuantity(
	                existingCartItem.getQuantity()
	                        + request.getQuantity()
	        );

	        cartItemRepository.save(
	                existingCartItem
	        );

	    } else {

	        CartItem cartItem =
	                CartItem.builder()
	                        .cart(cart)
	                        .foodItem(foodItem)
	                        .quantity(request.getQuantity())
	                        .build();

	        cartItemRepository.save(cartItem);

	        // VERY IMPORTANT
	        cart.getCartItems().add(cartItem);
	    }

	    return mapToCartResponse(cart);
	}

	private CartResponse mapToCartResponse(
	        Cart cart) {

	    List<CartItem> cartItems =
	            cartItemRepository.findByCartId(
	                    cart.getId()
	            );

	    List<CartItemResponse> itemResponses =

	            cartItems.stream()

	                    .map(item -> {

	                        BigDecimal subtotal =

	                                item.getFoodItem()
	                                        .getPrice()

	                                        .multiply(
	                                                BigDecimal.valueOf(
	                                                        item.getQuantity()
	                                                )
	                                        );

	                        return CartItemResponse
	                                .builder()

	                                .id(item.getId())

	                                .foodItemId(
	                                        item.getFoodItem()
	                                                .getId()
	                                )

	                                .foodItemName(
	                                        item.getFoodItem()
	                                                .getName()
	                                )

	                                .unitPrice(
	                                        item.getFoodItem()
	                                                .getPrice()
	                                )

	                                .quantity(
	                                        item.getQuantity()
	                                )

	                                .subtotal(
	                                        subtotal
	                                )

	                                .build();
	                    })

	                    .toList();

	    BigDecimal totalPrice =

	            itemResponses.stream()

	                    .map(
	                            CartItemResponse::getSubtotal
	                    )

	                    .reduce(
	                            BigDecimal.ZERO,
	                            BigDecimal::add
	                    );

	    int totalItems =

	            itemResponses.stream()

	                    .mapToInt(
	                            CartItemResponse::getQuantity
	                    )

	                    .sum();

	    return CartResponse.builder()

	            .id(cart.getId())

	            .userId(
	                    cart.getUser().getId()
	            )

	            .userName(
	                    cart.getUser().getName()
	            )

	            .items(itemResponses)

	            .totalPrice(totalPrice)

	            .totalItems(totalItems)

	            .build();
	}

	@Override
	public CartResponse getCartByUserId(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User id not found with id::"+userId));
	
		Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart not found user id with::"+userId));
		
		
		return mapToCartResponse(cart);
	}

	@Override
	public CartResponse updateCartItem(Long cartItemId, UpdateCartItemRequest request) {
		CartItem cartitem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("cart item not found with::"+cartItemId));
	
		cartitem.setQuantity(request.getQuantity());
		cartItemRepository.save(cartitem);
		Cart cart = cartitem.getCart();
		return mapToCartResponse(cart);
	}

	@Override
	public CartResponse removeCartItem(Long cartItemId) {
	CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("cart item  is not found::"+cartItemId));
	
	
	Long cartId = cartItem.getCart().getId();
	
	cartItemRepository.delete(cartItem);
	
	Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart not found::"));
	
	return mapToCartResponse(cart);
	
	
	}

	@Override
	public void clearCart(Long userId) {
	
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with id::"));
	
		Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart not found user with id::"));
	
		cartItemRepository.deleteAll(cart.getCartItems());
		
	}
}
