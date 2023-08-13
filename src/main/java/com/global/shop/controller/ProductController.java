package com.global.shop.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.shop.entity.Product;
import com.global.shop.error.CustomResponse;
import com.global.shop.error.SuccessResponsePage;
import com.global.shop.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	
	@Operation(summary = "Get a product by its id")
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return ResponseEntity.ok(new CustomResponse(productService.findById(id)));
	}
	
	@Operation(summary = "Get a product by its name")
	@GetMapping("name/{name}")
	public ResponseEntity<?> findByName(@PathVariable @NotBlank String name) {
		return ResponseEntity.ok(new CustomResponse(productService.findByName(name)));
	}
	
	@Operation(summary = "filter the products by its name")
	@GetMapping("/filter")
	public ResponseEntity<?> getProducts(@RequestParam(name = "name", required = false) String name) {
	    if (name != null) {
	        return ResponseEntity.ok(new CustomResponse(productService.findProductsByName(name)));
	    } else {
	        return ResponseEntity.ok(new CustomResponse(productService.findAll()));
	    }
	}
	
	@Operation(summary = "Get all products")
	@GetMapping("/all")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0")int pageNo , @RequestParam(defaultValue = "5") int pageSize
			                         ,@RequestParam(defaultValue = "id") String sortcol , @RequestParam(defaultValue = "true")Boolean isAsc) {
		int totalPages =(int) Math.ceil(productService.countAllProducts() / Double.valueOf(pageSize));
      return ResponseEntity.ok(new SuccessResponsePage(productService.findAll(pageNo, pageSize, sortcol, isAsc),pageNo,totalPages));
	}
	
	@Operation(summary = "Add new product")
	@PostMapping("/create")
	public ResponseEntity<?> createProduct(@RequestBody @Valid Product product){
		
		return ResponseEntity.ok(new CustomResponse(productService.createProduct(product)));
	}
	
	@Operation(summary = "update a product")
	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody @Valid Product product){
		
		return ResponseEntity.ok(new CustomResponse(productService.updateProduct(product)));
	}
	
	@Operation(summary = "Delete a product by its id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable long id){
		
		return ResponseEntity.ok(new CustomResponse(productService.deleteProduct(id)));
	}
}
