package com.desafiomobi.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;

@OpenAPIDefinition(info = @Info(title = "Desafio Mobi", version = "v1", description = "Documentação do desafio"))
public class OpenApiConfiguration {

	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.components(new Components())
				.info(new io.swagger.v3.oas.models.info.Info()
						.title("Desafio Mobi")
						.version("v1")
						.license(new License()
								.name("apache 2.0")
								.url("http://springdoc.org"))
				);
	}
	
}
