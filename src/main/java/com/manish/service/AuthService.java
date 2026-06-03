package com.manish.service;

import com.manish.DTO.Request.LoginRequest;
import com.manish.DTO.Request.RegisterRequest;
import com.manish.DTO.Response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
    
}
