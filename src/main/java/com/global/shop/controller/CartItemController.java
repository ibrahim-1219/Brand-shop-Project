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
import com.global.shop.entity.Product;
import com.global.shop.error.CustomResponse;
import com.global.shop.error.SuccessResponsePage;
import com.global.shop.service.CartItemService;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {
	
	@Autowired
	private CartItemService cartItemService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return ResponseEntity.ok(new CustomResponse(cartItemService.findById(id)));
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0")int pageNo , @RequestParam(defaultValue = "5") int pageSize
			                         ,@RequestParam(defaultValue = "id") String sortcol , @RequestParam(defaultValue = "true")Boolean isAsc) {
		int totalPages =(int) Math.ceil(cartItemService.countAllCartItems() / Double.valueOf(pageSize));
      return ResponseEntity.ok(new SuccessResponsePage(cartItemService.findAll(pageNo, pageSize, sortcol, isAsc),pageNo,totalPages));
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addCartItem(@RequestParam long cartId,@RequestParam long productId,@RequestParam(defaultValue = "1") int quantity ){
		
		return ResponseEntity.ok(new CustomResponse(cartItemService.addCartItem(cartId,productId,quantity)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCart(@PathVariable long id){
		
		return ResponseEntity.ok(new CustomResponse(cartItemService.deleteCartItem(id)));
	}
	

}
