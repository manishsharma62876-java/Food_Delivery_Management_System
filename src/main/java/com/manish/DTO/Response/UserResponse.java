package com.manish.DTO.Response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

	private Long id;
	private String name;
	private String email;
	private String phone;
	private String address;
	private LocalDateTime createdAt;
}
