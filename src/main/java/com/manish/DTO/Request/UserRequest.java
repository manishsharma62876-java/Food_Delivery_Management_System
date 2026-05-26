package com.manish.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequest {

	@NotBlank(message = "Name is Required")
	private String name;

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is Required")
	private String email;

	@NotBlank(message = "phone is required")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian phone number")
	private String phone;

	@NotBlank(message = "Address is Required")
	private String address;
}
