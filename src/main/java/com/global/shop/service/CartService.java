package com.global.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.global.shop.entity.Cart;
import com.global.shop.entity.Product;
import com.global.shop.error.CustomException;
import com.global.shop.repository.CartRepo;

@Service
public class CartService {
	
	@Autowired
	private CartRepo cartRepo;

	public List<Cart> findAll() {
	
		return cartRepo.findAll();
	}

	public List<Cart> insertAll(List<Cart> asList) {
     
		return cartRepo.saveAll(asList);
		
	}
	
	public Cart findById(long id) {
		
		return cartRepo.findById(id).orElseThrow(()-> new CustomException("This Cart is not found"));
	}

	public Long countAllCarts() {
		
		return cartRepo.count();
	}

	public  Page<Cart> findAll(int pageNo,int pageSize,String sortCol,Boolean isAsc) {
		
		Sort sort = Sort.by(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortCol);
	    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		return cartRepo.findAllCarts(pageable);
	}

	public Cart createCart() {
		Cart cart= new Cart() ;
		return cartRepo.save(cart);
	}

	public int deleteCart(long id) {
		
		Cart cart = cartRepo.findById(id).orElseThrow(()-> new CustomException("This Cart is not found"));

		return cartRepo.deleteById(id);
	}
	




}
