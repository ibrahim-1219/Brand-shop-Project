package com.global.shop.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableCaching
@EnableAspectJAutoProxy
public class WebConfig {
	
	@Bean
	public AuditorAware<String> auditorAware() {
		
		return new AuditorAwareImpl();
		
		}
	  @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }

}
