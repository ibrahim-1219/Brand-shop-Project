package com.global.shop.controller;

import javax.validation.Valid;

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

import com.global.shop.entity.Inventory;
import com.global.shop.error.CustomResponse;
import com.global.shop.error.SuccessResponsePage;
import com.global.shop.service.InventoryService;

@RestController
@RequestMapping("/inventories")
public class InventoryController {
	
	@Autowired
	private InventoryService inventoryService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createInventory(@RequestBody @Valid Inventory inventory)
	{
		return ResponseEntity.ok(new CustomResponse(inventoryService.insert(inventory)));
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return ResponseEntity.ok(new CustomResponse(inventoryService.findById(id)));
	}
	@GetMapping("/all")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0")int pageNo , @RequestParam(defaultValue = "5") int pageSize
			                         ,@RequestParam(defaultValue = "id") String sortcol , @RequestParam(defaultValue = "true")Boolean isAsc) {
		int totalPages =(int) Math.ceil(inventoryService.countAllInvetories() / Double.valueOf(pageSize));
      return ResponseEntity.ok(new SuccessResponsePage(inventoryService.findAll(pageNo, pageSize, sortcol, isAsc),pageNo,totalPages));
	}
	@PostMapping("/update")
	public ResponseEntity<?> updateInventory(@RequestBody @Valid Inventory inventory){
		
		return ResponseEntity.ok(new CustomResponse(inventoryService.updateInventory(inventory)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteInventory(@PathVariable long id){
		
		return ResponseEntity.ok(new CustomResponse(inventoryService.deleteInventory(id)));
	}

}
