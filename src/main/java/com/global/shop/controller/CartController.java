package com.global.shop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.shop.entity.Cart;
import com.global.shop.entity.Customer;
import com.global.shop.error.CustomResponse;
import com.global.shop.error.SuccessResponsePage;
import com.global.shop.service.CartService;


@RestController
@RequestMapping("/carts")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return ResponseEntity.ok(new CustomResponse(cartService.findById(id)));
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0")int pageNo , @RequestParam(defaultValue = "5") int pageSize
			                         ,@RequestParam(defaultValue = "id") String sortcol , @RequestParam(defaultValue = "true")Boolean isAsc) {
		int totalPages =(int) Math.ceil(cartService.countAllCarts() / Double.valueOf(pageSize));
      return ResponseEntity.ok(new SuccessResponsePage(cartService.findAll(pageNo, pageSize, sortcol, isAsc),pageNo,totalPages));
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createProduct(){
		
		return ResponseEntity.ok(new CustomResponse(cartService.createCart()));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCart(@PathVariable long id){
		
		return ResponseEntity.ok(new CustomResponse(cartService.deleteCart(id)));
	}
	

}
