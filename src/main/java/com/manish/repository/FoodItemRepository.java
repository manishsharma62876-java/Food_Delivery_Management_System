package com.manish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manish.entity.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long>{

}
