package com.manish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manish.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
