package com.turanbalayev.simpleblogapp;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Simple Blog Rest API",
				description = "Simple Blog Rest API documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Turan Balayev",
						email = "turanbalayev96@gmail.com",
						url = "https://www.turanbalayev.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.turanbalayev.com/license"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog Rest API documentation",
				url = "https://github.com/turanbalayev/simple-blog-app"
		)
)
public class SimpleBlogAppApplication {


	public static void main(String[] args) {
		SpringApplication.run(SimpleBlogAppApplication.class, args);
	}


	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
