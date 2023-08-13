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
public class ProductDto {
	
	
	    private long id;
	    private String title;
	    private String description;
	    private double price;
	    private double discountPercentage;
	    private double rating;
	    private int stock;
	    private String brand;
	    private String category;
	    private String thumbnail;
	    private List<String> images;

	}


