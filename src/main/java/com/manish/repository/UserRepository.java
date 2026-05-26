package com.manish.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manish.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	Optional<User> findByName(String name);
	
}
