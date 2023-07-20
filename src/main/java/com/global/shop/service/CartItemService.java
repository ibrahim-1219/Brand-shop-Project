package com.global.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.global.shop.entity.Cart;
import com.global.shop.entity.CartItem;
import com.global.shop.entity.Product;
import com.global.shop.error.CustomException;
import com.global.shop.repository.CartItemRepo;

@Service
public class CartItemService {
	
	@Autowired 
	private CartItemRepo cartItemRepo;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;

	public List<CartItem> findAll() {
	
		return cartItemRepo.findAll();
	}

	public List<CartItem> insertAll(List<CartItem> asList) {
	
		return cartItemRepo.saveAll(asList);
		
	}

    public CartItem findById(long id) {
		
		return cartItemRepo.findById(id).orElseThrow(()-> new CustomException("This Cart is not found"));
	}

	public Long countAllCartItems() {

		return cartItemRepo.count();
	}

	public Page<CartItem> findAll(int pageNo, int pageSize, String sortcol, Boolean isAsc) {
		Sort sort = Sort.by(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortcol);
	    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		return cartItemRepo.findAllCartItems(pageable);
	}

	public int deleteCartItem(long id) {
	
		CartItem cartItem = cartItemRepo.findById(id).orElseThrow(()-> new CustomException("This Cart Item is not found"));
		return cartItemRepo.deleteById(id);
	}

	public CartItem addCartItem(long cartId, long productId, int quantity) {
		Cart cartToAdd = cartService.findById(cartId);
		Product productToAdd= productService.findById(productId);
		
		CartItem cartItem=new CartItem();
		cartItem.setCart(cartToAdd);
		cartItem.setProduct(productToAdd);
		cartItem.setQuantity(quantity);
		cartItem.setTotalPrice(quantity);
		
		return cartItemRepo.save(cartItem);
	}



	


}
