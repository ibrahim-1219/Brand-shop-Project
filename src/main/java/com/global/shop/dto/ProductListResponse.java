package com.global.shop.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse {
	
    private List<ProductDto> products;
    private int total;
    private int skip;
    private int limit;

    // Add getters and setters for all the properties
}
