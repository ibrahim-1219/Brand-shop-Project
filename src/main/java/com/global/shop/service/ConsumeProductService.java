package com.global.shop.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.global.shop.dto.ProductDto;
import com.global.shop.dto.ProductListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumeProductService {
	
	private final RestTemplate restTemplate;

	private static String BASE_POST_URL = "https://dummyjson.com/products";



	public ProductListResponse getAllPost() {

//		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<ProductListResponse> result = restTemplate.getForEntity(BASE_POST_URL, ProductListResponse.class);

		return result.getBody();

	}

  


}
