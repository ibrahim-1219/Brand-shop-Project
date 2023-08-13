package com.global.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.global.shop.entity.Customer;
import com.global.shop.projection.ProductDto;


public interface CustomerRepo extends JpaRepository<Customer, Long>{
	
	 Optional<Customer> findByEmail(String email);

	 Optional<Customer> findByName(String name);
	 
	 Optional<Customer> findById(long id);
	 
	 //@Query(value = "SELECT * from user_products where user_id = user.getId();" , nativeQuery = true)
     //Optional<Product> findMyCart(User user);
	 
	 @Query(value = "SELECT p.id,p.description,\r\n"
	 		+ "	 		p.name,\r\n"
	 		+ "	 		p.price\r\n"
	 		+ "	 		FROM customers c\r\n"
	 		+ "	 		JOIN customer_products up ON c.id = up.customer_id\r\n"
	 		+ "	 		JOIN products p ON p.id = up.product_id\r\n"
	 		+ "	 		WHERE c.id = :customerId", nativeQuery = true)
	 List<ProductDto> findMyCart(@Param("customerId") long customerId);
	 
	 
	 @Query(value = "SELECT * FROM customers", nativeQuery = true)
	 Page<Customer> findAllCustomers(Pageable pageable);
	 
	 @Transactional
	 @Modifying
	 @Query(value = "DELETE FROM `customers` WHERE id=:id" , nativeQuery = true)
	 int deleteById(long id);
	 
	 




}
