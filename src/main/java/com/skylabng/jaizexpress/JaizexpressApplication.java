package com.skylabng.jaizexpress;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class JaizexpressApplication {
	@Value("${klikpay.token}")
	private String klikPayToken;

	public static void main(String[] args) {

		SpringApplication.run(JaizexpressApplication.class, args);
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().addSecurityItem(new SecurityRequirement().
						addList("Bearer Authentication"))
				.components(new Components().addSecuritySchemes
						("Bearer Authentication", createAPIKeyScheme()))
				.info(new Info().title("Jaiz Express ")
						.description("Some custom description of API.")
						.version("1.0").contact(new Contact().name("Sallo Szrajbman")
								.email( "www.baeldung.com").url("salloszraj@gmail.com"))
						.license(new License().name("License of API")
								.url("API license URL")));
	}

	@Bean(name="RestTemplateBearer")
	public RestTemplate restTemplateTokenAuth(RestTemplateBuilder builder) {
		String auth = "Bearer " + klikPayToken;
		builder.defaultHeader("Content-type", "application/json");
		builder.defaultHeader("Accept", "application/json");
		builder.defaultHeader("Authorization", auth);
		return builder.build();
	}

	private SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP)
				.bearerFormat("JWT")
				.scheme("bearer");
	}
}
