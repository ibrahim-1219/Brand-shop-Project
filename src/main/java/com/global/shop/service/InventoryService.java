package com.global.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.global.shop.entity.Inventory;
import com.global.shop.entity.Product;
import com.global.shop.error.CustomException;
import com.global.shop.repository.InventoryRepo;
import com.global.shop.repository.ProductRepo;

@Service
public class InventoryService {
	
	@Autowired
	private InventoryRepo inventoryRepo;
	
	@Autowired
	private ProductRepo productRepo;

	public Inventory insert(Inventory inventory) {
		
	    long id = inventory.getProduct().getId();
		Optional<Product> product = productRepo.findById(inventory.getProduct().getId());
		
		if(product.isEmpty())
		{
			throw new CustomException("this product is not found");
		}
		return inventoryRepo.save(inventory);
	}

	public Inventory findById(long id) {
		
		return inventoryRepo.findById(id).orElseThrow(()-> new CustomException("This Inventory is not found"));
	}

	public long countAllInvetories() {
		
		return inventoryRepo.count();
	}

	public Page<Inventory> findAll(int pageNo, int pageSize, String sortCol, Boolean isAsc) {
	
		Sort sort = Sort.by(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortCol);
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		return inventoryRepo.findAll(pageable);
	}
	
    public Inventory updateInventory(Inventory inventory) {
		
    	Inventory inventoryToUpdate = inventoryRepo.findById(inventory.getId()).orElseThrow(()-> new CustomException("this inventory not found"));
	    inventoryToUpdate.setProduct(inventory.getProduct());
	    inventoryToUpdate.setQuantity(inventory.getQuantity());
	    Inventory newInventory = inventoryRepo.save(inventoryToUpdate);
		return newInventory;
	}

	public int deleteInventory(long id) {
    	Inventory inventoryToUpdate = inventoryRepo.findById(id).orElseThrow(()-> new CustomException("this inventory not found"));
		return inventoryRepo.deleteById(id);
	}

	public List<Inventory> findAll() {
	
		return inventoryRepo.findAll();
	}
	


}
