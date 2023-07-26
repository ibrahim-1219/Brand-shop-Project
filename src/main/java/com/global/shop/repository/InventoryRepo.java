package com.global.shop.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.global.shop.entity.Inventory;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long>{
	
	 @Transactional
	 @Modifying
	 @Query(value = "DELETE FROM `inventories` WHERE id=:id" , nativeQuery = true)
	 int deleteById(long id);

	 
	 @Query(value = "SELECT `quantity` FROM `inventories` WHERE product_id=:id ",nativeQuery = true)
	int getQuantity(@Param("id")long productId);

	 @Transactional
	 @Modifying
	 @Query(value = "UPDATE `inventories` SET `quantity`=:quantity WHERE `product_id`=:productId" , nativeQuery = true)
	void updateInventoryQuantity(Long productId,int quantity);



}
