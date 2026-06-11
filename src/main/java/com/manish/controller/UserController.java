package com.manish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manish.DTO.Request.UserRequest;
import com.manish.DTO.Response.ApiResponse;
import com.manish.DTO.Response.UserResponse;
import com.manish.service.UserService;



@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/save")
	public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserRequest request){
	return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("User created successfullyy",userService.createUser(request)));}
	@GetMapping("/allUsers")
	public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(){
	return ResponseEntity.ok(ApiResponse.success("All users fetched successfuly",userService.getAllUsers()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id){
		return ResponseEntity.ok(ApiResponse.success("User fetched successfullyyyyy",userService.getUserById(id)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponse>>  updateUser(@PathVariable Long id , @RequestBody UserRequest request){
		return ResponseEntity.ok(ApiResponse.success("user updated successfullyy", userService.updateUser(id, request)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id){
		userService.deleteUserById(id);
		return ResponseEntity.ok(ApiResponse.success("user data deleted succeesfully",null));
		
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<ApiResponse<UserResponse>>  getUserByEmail(@PathVariable String email){
		return  ResponseEntity.ok(ApiResponse.success("User email fetched successfullyy",userService.findByemail(email)));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<ApiResponse<UserResponse>> getByName(@PathVariable String name){
		return ResponseEntity.ok(ApiResponse.success("User name fethed successfulyy",userService.findByName(name)));
	}
	
	@PostMapping("/saveAll")
	public ResponseEntity<ApiResponse<List<UserResponse>>> createMultipleUsers(@RequestBody List<UserRequest>  request){
		return ResponseEntity.ok(ApiResponse.success("User saved with multiple data",userService.createMultipleUsers(request)));
	}
	
}
