package com.manish.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.manish.DTO.Request.LoginRequest;
import com.manish.DTO.Request.RegisterRequest;
import com.manish.DTO.Response.AuthResponse;
import com.manish.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(

			@Valid @RequestBody RegisterRequest request) {

		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(

			@Valid @RequestBody LoginRequest request) {

		return ResponseEntity.ok(authService.login(request));
	}
}