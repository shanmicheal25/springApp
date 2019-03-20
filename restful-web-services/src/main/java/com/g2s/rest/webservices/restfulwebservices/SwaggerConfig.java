package com.g2s.rest.webservices.restfulwebservices;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import io.swagger.models.Contact;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// configuration
//Enable swagger
@Configuration // it will help to tell as spring bean
@EnableSwagger2
public class SwaggerConfig {

	public static final springfox.documentation.service.Contact DEFAULT_CONTACT = new springfox.documentation.service.Contact(
			"Manoharan Shankar", "", "shanmicheal25@gmail.com");
	public static final ApiInfo DEFAULT_AI_INFO = new ApiInfo("User Api Documentation", "User Api Documentation", "1.0",
			"urn:tos", DEFAULT_CONTACT, "G2S 2.0", "http://www.g2s.org/licenses/LICENSE-2.0");

	public static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
			Arrays.asList("application/json", "application/xml"));

	// Bean docket
	// swagger 2
	// All the paths
	// all api
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_AI_INFO).produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES);
	}
}
