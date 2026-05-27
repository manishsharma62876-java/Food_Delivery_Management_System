package com.manish.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {

		return new OpenAPI()

				.info(

						new Info()

								.title("Food Delivery API")

								.version("1.0")

								.description("Food Delivery Backend Project APIs developed by Mr.manish"));
	}
}
