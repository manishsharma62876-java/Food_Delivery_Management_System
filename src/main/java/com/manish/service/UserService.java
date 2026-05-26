package com.manish.service;

import java.util.List;

import com.manish.DTO.Request.UserRequest;
import com.manish.DTO.Response.UserResponse;

public interface UserService {

	UserResponse createUser (UserRequest request);
	
	List<UserResponse>  getAllUsers();
	
	UserResponse getUserById(Long id);
	
	UserResponse updateUser(Long id , UserRequest request);
	
	void deleteUserById(Long id);
	
	UserResponse  findByemail(String email);
	
	UserResponse findByName(String name);
	
	List<UserResponse> createMultipleUsers(List<UserRequest>  request);
	
	
}
