package com.global.shop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.shop.entity.Customer;
import com.global.shop.error.CustomResponse;
import com.global.shop.error.SuccessResponsePage;
import com.global.shop.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Operation(summary = "Add new customer")
	@PostMapping("/create")
	public ResponseEntity<?> createCustomer(@RequestBody @Valid Customer customer) {
		
		return ResponseEntity.ok(new CustomResponse(customerService.insert(customer)));
	}
	 
	@Operation(summary = "update a customer")
	@PutMapping("/update")
	public ResponseEntity<?> updateEmail(@RequestParam long id , @RequestParam String email) {
		
		return ResponseEntity.ok(new CustomResponse(customerService.updateEmail(id,email)));
	}
	
	@Operation(summary = "Get a customer by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the customer", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "customer not found", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<?> findById (@PathVariable long id){
		
		return ResponseEntity.ok(new CustomResponse(customerService.findById(id)));
	}
	
	@Operation(summary = "Get all customers")
	@GetMapping("/all")
	public ResponseEntity<?> findAll (@RequestParam(defaultValue = "0") int pageNo ,@RequestParam(defaultValue = "5") int pageSize
			                            ,@RequestParam(defaultValue = "id") String sortCol , @RequestParam(defaultValue = "true") Boolean isAsc){

		int totalPages = (int) Math.ceil(customerService.countAllCustomers() / Double.valueOf(pageSize));
		Page<Customer> page =customerService.findAllCustomers(pageNo ,pageSize,sortCol,isAsc);
		return ResponseEntity.ok(new SuccessResponsePage(page,pageNo,totalPages));
	}
//	@GetMapping("/all")
//	public ResponseEntity<?> findAll (@RequestParam(defaultValue = "0") int pageNo ,@RequestParam(defaultValue = "5") int pageSize
//			                            ,@RequestParam(defaultValue = "id") String sortCol , @RequestParam(defaultValue = "true") Boolean isAsc){
//
//		return ResponseEntity.ok(new CustomResponse(customerService.findAll(pageNo, pageSize)));
//	}
	
	@Operation(summary = "Delete a customer by its id")
	@DeleteMapping("/{id}")
	public ResponseEntity<CustomResponse> deleteById(@PathVariable long id){
		
		return ResponseEntity.ok(new CustomResponse(customerService.deleteById(id)));
	}
	

	
}
