package com.manish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manish.entity.CartItem;
import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	CartItem findByCartIdAndFoodItemId(  Long cartId, Long foodItemId);
	
	List<CartItem> findByCartId(Long cartId);	
	
	void deleteByCartId(Long cartId);
}
