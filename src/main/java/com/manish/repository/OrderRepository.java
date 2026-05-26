package com.manish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manish.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
