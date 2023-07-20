package com.global.shop.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.global.shop.entity.CartItem;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long>{
	
	@Query(value ="SELECT * FROM cart_items" ,nativeQuery = true )
	Page<CartItem> findAllCartItems(Pageable pageable);

	 @Transactional
	 @Modifying
	 @Query(value = "DELETE FROM `cart_items` WHERE id=:id" , nativeQuery = true)
	 int deleteById(long id);
}
