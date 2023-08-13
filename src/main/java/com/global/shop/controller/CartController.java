package com.global.shop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.shop.entity.Cart;
import com.global.shop.entity.Customer;
import com.global.shop.error.CustomResponse;
import com.global.shop.error.SuccessResponsePage;
import com.global.shop.service.CartService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/carts")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Operation(summary = "Get a cart by its id")
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return ResponseEntity.ok(new CustomResponse(cartService.findById(id)));
	}
	
	@Operation(summary = "Get all carts")
	@GetMapping("/all")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0")int pageNo , @RequestParam(defaultValue = "5") int pageSize
			                         ,@RequestParam(defaultValue = "id") String sortcol , @RequestParam(defaultValue = "true")Boolean isAsc) {
		int totalPages =(int) Math.ceil(cartService.countAllCarts() / Double.valueOf(pageSize));
      return ResponseEntity.ok(new SuccessResponsePage(cartService.findAll(pageNo, pageSize, sortcol, isAsc),pageNo,totalPages));
	}
	
	@Operation(summary = "Add new cart")
	@PostMapping("/create")
	public ResponseEntity<?> createProduct(){
		
		return ResponseEntity.ok(new CustomResponse(cartService.createCart()));
	}
	
	@Operation(summary = "Delete a cart by its id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCart(@PathVariable long id){
		
		return ResponseEntity.ok(new CustomResponse(cartService.deleteCart(id)));
	}
	
	@Operation(summary = "add a product to the cart")
	@PutMapping("/add")
	public  ResponseEntity<?> addProductToCart(@RequestParam long cartId,@RequestParam String productName){
		if(cartService.addProductToCart(cartId, productName)!=0)
		{
		return ResponseEntity.ok(new CustomResponse("added"));
		}
		return null;

	}
	
	@Operation(summary = "delete a product from the cart")
	@DeleteMapping("/delete-product")
	public  ResponseEntity<?> deleteProductFromCart(@RequestParam long cartId,@RequestParam String productName){
		
	    if(cartService.deleteProductFromCart(cartId, productName)!=0)
	    {
		return ResponseEntity.ok(new CustomResponse("deleted"));
	    }else {
	    	return ResponseEntity.ok(new CustomResponse("Not Found"));
	    }
	}

}
