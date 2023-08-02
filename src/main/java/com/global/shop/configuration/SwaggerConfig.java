package com.global.shop.configuration;

import java.util.Collections;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public GroupedOpenApi userManagementApi() {
		String packagesToscan[] = { "com.global.start" };
		return GroupedOpenApi.builder()
		                     .group("User Management API")
							 .packagesToScan(packagesToscan)
							 .addOperationCustomizer(appTokenHeaderParam())
							 .build();
	}
	
	@Bean
	public GroupedOpenApi setupApi() {
		String packagesToscan[] = { "com.global.shop" };
		return GroupedOpenApi.builder()
		                     .group("Brandshop API")
							 .packagesToScan(packagesToscan)
							 .addOperationCustomizer(appTokenHeaderParam())
							 .build();
	}
	
	@Bean
    public OperationCustomizer appTokenHeaderParam() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            Parameter headerParameter = new Parameter().in(ParameterIn.HEADER.toString()).required(false).
                    schema(new StringSchema()._default("app_token_header_default_value")).name("app_token_header").description("App Token Header");
            operation.addParametersItem(headerParameter);
            return operation;
        };
    }
	

}
	
	

