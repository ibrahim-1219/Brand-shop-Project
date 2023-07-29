package com.global.shop.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.global.shop.entity.Cart;
import com.global.shop.entity.Product;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long > {

	@Query(value ="SELECT * FROM carts" ,nativeQuery = true )
	Page<Cart> findAllCarts(Pageable pageable);
	
	
	 @Transactional
	 @Modifying
	 @Query(value = "DELETE FROM `carts` WHERE id=:id" , nativeQuery = true)
	 int deleteById(long id);

	@Transactional
	@Modifying
    @Query(value = "INSERT INTO `cart_products`(`cart_id`, `product_id`) VALUES (:cartId,:productId)",nativeQuery = true)
	int addProductToCart(@Param("cartId") long cartId,@Param("productId") long productId);

	@Transactional
	@Modifying
    @Query(value = "DELETE FROM `cart_products` WHERE cart_id=:cartId AND product_id=:productId",nativeQuery = true)
	int deleteProductFromCart(@Param("cartId") long cartId,@Param("productId") long productId);
	
	
	@Query(value = "SELECT COUNT(*) AS total_count FROM `cart_products` WHERE cart_id =:cartId AND product_id = :productId",nativeQuery = true)
	int countProductItemsInCart(@Param("cartId") long cartId,@Param("productId") long productId);

}
