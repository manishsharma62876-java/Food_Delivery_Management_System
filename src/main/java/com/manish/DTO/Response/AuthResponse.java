package com.manish.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

	private String token;
}
