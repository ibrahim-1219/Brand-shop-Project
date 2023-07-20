package com.global.shop.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.global.shop.entity.Inventory;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long>{
	
	 @Transactional
	 @Modifying
	 @Query(value = "DELETE FROM `inventories` WHERE id=:id" , nativeQuery = true)
	 int deleteById(long id);

}
