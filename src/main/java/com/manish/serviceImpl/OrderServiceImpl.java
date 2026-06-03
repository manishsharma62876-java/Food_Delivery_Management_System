package com.manish.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manish.DTO.Request.PlaceOrderRequest;
import com.manish.DTO.Request.UpdateOrderStatusRequest;
import com.manish.DTO.Response.OrderItemResponse;
import com.manish.DTO.Response.OrderResponse;
import com.manish.entity.Cart;
import com.manish.entity.CartItem;
import com.manish.entity.Order;
import com.manish.entity.OrderItem;
import com.manish.entity.OrderStatus;
import com.manish.entity.Restaurant;
import com.manish.entity.User;
import com.manish.exception.BadRequestException;
import com.manish.exception.ResourceNotFoundException;
import com.manish.repository.CartItemRepository;
import com.manish.repository.CartRepository;
import com.manish.repository.OrderItemRepository;
import com.manish.repository.OrderRepository;
import com.manish.repository.UserRepository;
import com.manish.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	@Override
	@Transactional
	public OrderResponse placeOrder(PlaceOrderRequest request) {
		
		  User user =
		            userRepository.findById(
		                    request.getUserId()
		            ).orElseThrow(() ->
		                    new ResourceNotFoundException(
		                            "User not found"
		                    ));
		  
		  Cart cart =
		            cartRepository.findByUserId(
		                    user.getId()
		            ).orElseThrow(() ->
		                    new ResourceNotFoundException(
		                            "Cart not found"
		                    ));
		  
		  List<CartItem> cartItems =
		            cartItemRepository.findByCartId(
		                    cart.getId()
		            );

		    if (cartItems.isEmpty()) {
		        throw new BadRequestException(
		                "Cart is empty"
		        );
		    }
		
		    Restaurant restaurant =
		            cartItems.get(0)
		                    .getFoodItem()
		                    .getRestaurant();

		    BigDecimal totalAmount =
		            cartItems.stream()
		                    .map(CartItem::getSubtotal)
		                    .reduce(
		                            BigDecimal.ZERO,
		                            BigDecimal::add
		                    );
		    
		    Order order =
		            Order.builder()
		                    .user(user)
		                    .restaurant(restaurant)
		                    .deliveryAddress(
		                            request.getDeliveryAddress()
		                    )
		                    .notes(
		                            request.getNotes()
		                    )
		                    .status(OrderStatus.PENDING)
		                    .totalAmount(totalAmount)
		                    .build();
		    
		    Order savedOrder =
		            orderRepository.save(order);

		    List<OrderItem> orderItems =
		            new ArrayList<>();

		    for (CartItem cartItem : cartItems) {

		        OrderItem orderItem =
		                OrderItem.builder()
		                        .order(savedOrder)
		                        .foodItem(
		                                cartItem.getFoodItem()
		                        )
		                        .quantity(
		                                cartItem.getQuantity()
		                        )
		                        .unitPrice(
		                                cartItem.getFoodItem()
		                                        .getPrice()
		                        )
		                        .build();
		        
		        
		        orderItems.add(orderItem);
		    }

		    orderItemRepository.saveAll(orderItems);

		    savedOrder.setOrderItems(orderItems);

		    cartItemRepository.deleteAll(cartItems);

		    return mapToOrderResponse(savedOrder);
	}


	private OrderResponse mapToOrderResponse(Order savedOrder) {
		
		  List<OrderItemResponse> itemResponses =

				  savedOrder.getOrderItems()
		                    .stream()
		                    .map(item ->

		                            OrderItemResponse.builder()

		                                    .id(item.getId())

		                                    .foodItemId(
		                                            item.getFoodItem().getId()
		                                    )

		                                    .foodItemName(
		                                            item.getFoodItem().getName()
		                                    )

		                                    .unitPrice(
		                                            item.getUnitPrice()
		                                    )

		                                    .quantity(
		                                            item.getQuantity()
		                                    )

		                                    .subtotal(
		                                            item.getSubtotal()
		                                    )

		                                    .build()

		                    )
		                    .toList();

		    return OrderResponse.builder()

		            .id(savedOrder.getId())

		            .userId(
		            		savedOrder.getUser().getId()
		            )

		            .userName(
		            		savedOrder.getUser().getName()
		            )

		            .restaurantId(
		            		savedOrder.getRestaurant().getId()
		            )

		            .restaurantName(
		            		savedOrder.getRestaurant().getName()
		            )

		            .status(
		            		savedOrder.getStatus()
		            )

		            .totalAmount(
		            		savedOrder.getTotalAmount()
		            )

		            .deliveryAddress(
		            		savedOrder.getDeliveryAddress()
		            )

		            .notes(
		            		savedOrder.getNotes()
		            )

		            .orderItems(
		                    itemResponses
		            )

		            .createdAt(
		            		savedOrder.getCreatedAt()
		            )

		            .updatedAt(
		            		savedOrder.getUpdatedAt()
		            )

		            .build();
	}


	@Override
	public OrderResponse getOrderById(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("id not found with::"+orderId));
	
		return mapToOrderResponse(order);
	}


	@Override
	public List<OrderResponse> getOrdersByUserId(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id::"+userId));
	
		List<Order> orders = orderRepository.findByUserId(userId);
		
		return orders.stream().map(order -> mapToOrderResponse(order)).toList();
	}


	@Override
	public OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
		
//	Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found with id::"));)
	
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found with id::"));
 
		order.setStatus(request.getStatus());
    
    Order updatedsave = orderRepository.save(order);
  
    return mapToOrderResponse(updatedsave);
	}
	
		@Override
		public OrderResponse cancelOrder(Long orderId) {

		    Order order =
		            orderRepository.findById(orderId)
		                    .orElseThrow(() ->
		                            new ResourceNotFoundException(
		                                    "Order not found with id : " + orderId
		                            ));

		    if (order.getStatus() == OrderStatus.DELIVERED) {

		        throw new BadRequestException(
		                "Delivered order cannot be cancelled"
		        );
		    }

		    if (order.getStatus() == OrderStatus.CANCELLED) {

		        throw new BadRequestException(
		                "Order is already cancelled"
		        );
		    }

		    order.setStatus(OrderStatus.CANCELLED);

		    Order cancelledOrder =
		            orderRepository.save(order);

		    return mapToOrderResponse(cancelledOrder);
		}
	}


