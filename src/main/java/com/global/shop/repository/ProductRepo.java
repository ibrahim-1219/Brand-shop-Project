package com.global.shop.repository;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.global.shop.entity.Product;



public interface ProductRepo extends JpaRepository<Product, Long>{


	@Query(value ="SELECT * FROM products" ,nativeQuery = true )
	Page<Product> findAllProducts(Pageable pageable);

	@Query(value="SELECT * FROM `products` WHERE name=:name and description=:description and price=:price",nativeQuery = true)
	Product findByNameAndDescription(String name,double price ,String description);

	Optional <Product> findByName(String name);
	
	 @Transactional
	 @Modifying
	 @Query(value = "DELETE FROM `products` WHERE id=:id" , nativeQuery = true)
	 int deleteById(long id);

	List<Product> findByNameContaining(String name);
	

	
	
	

}
