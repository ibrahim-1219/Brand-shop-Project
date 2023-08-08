package com.global.shop.service;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.global.shop.error.CustomException;
import com.global.shop.entity.Product;
import com.global.shop.entity.Role;
import com.global.shop.entity.Cart;
import com.global.shop.entity.Customer;
import com.global.shop.projection.ProductDto;
import com.global.shop.repository.CustomerRepo;
import com.global.shop.security.AppUserDetail;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.attribute.AnnotationAppender.Target.OnType;


@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService{
	
	
	private final CustomerRepo customerRepo;
	
	
	private  final RoleService roleService;
	
	
	private final CartService cartService ;
		
	
	private final PasswordEncoder passwordEncoder;

	public Customer findById(long id) {
		
		return customerRepo.findById(id).orElseThrow(() -> new CustomException("Customer is not found"));

	}
    public Optional<Customer> findByEmail(String email) {
		
		Optional<Customer> customer = customerRepo.findByEmail(email);
	
		return customer;
	}

	public Page<Customer> findAllCustomers(int pageNo ,int pageSize,String sortCol ,Boolean isAsc) {
	
	    Sort sort = Sort.by(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortCol);
	    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
	    return customerRepo.findAll(pageable);
	  
	}
	public Map<String, Object> findAll(Integer pageNo, Integer pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Customer> pagedResult = customerRepo.findAll(paging);
		List<Customer> cutomers;
		if (pagedResult.hasContent()) {
			cutomers = pagedResult.getContent();
		} else {
			cutomers = new ArrayList<Customer>();
		}

		Map<String, Object> response = new HashMap<>();
		response.put("customers", cutomers);
		response.put("pageNumber", pageNo);
		response.put("totalPages",(int) Math.ceil(customerRepo.count() / Double.valueOf(pageSize)));
		return response;
	}
    public List<Customer> findAll () {
	
		return customerRepo.findAll();
	}
	public  List<ProductDto> findMyCart(long customerId){
		

		List<ProductDto> productList = customerRepo.findMyCart(customerId);
	
	
		return productList;
	}

	public List<Customer> insertAll(List<Customer> customers) {

		return customerRepo.saveAll(customers);
	}
	
	@Transactional
	 public Customer insert(Customer customer)
	   {
	       if(!customerRepo.findByEmail(customer.getEmail()).isEmpty())	 
	       {
	    	   throw new CustomException("Email address already exists");
	       }
		   Customer customerNew = new Customer();
		   customerNew.setName(customer.getName());
		   customerNew.setEmail(customer.getEmail());
		   customerNew.setPassword(passwordEncoder.encode(customer.getPassword()));
		   customerNew.setActive(true);
		   customerNew.setCreatedDateTime(LocalDateTime.now());
			 // Get the customer role entity by name
		    Role userRole = roleService.findRoleByName("USER_ROLE");
		    // Make sure the role exists
		    if (userRole == null) {
		        throw new RuntimeException("USER_ROLE not found in database!");
		    }
		    // Add the role to the customer's set of roles
		    Set<Role> roles = new HashSet<>();
		    roles.add(userRole);
		    customerNew.setRoles(roles);
		    Cart cart = cartService.createCart();
		    customerNew.setCart(cart);  
		   
		   return customerRepo.save(customerNew);
	   }
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		  Optional<Customer> customer =	customerRepo.findByEmail(email);
		  
		  if (!((Optional<Customer>) customer).isPresent()) {
			  
			  throw new UsernameNotFoundException("This User Not found with selected user name :- " + email);
		  }
			
			return new AppUserDetail(customer.get());
	}
	public Customer updateEmail(long id , String email) {
		Customer customerToUpdate = customerRepo.findById(id).orElseThrow(() -> new CustomException("Customer is not found"));
		Optional<Customer> customer = customerRepo.findByEmail(email);
		if(!customer.isEmpty() && customer.get().getId()!=id)
		{
			 throw new CustomException("This Email is already found ");
		}
		customerToUpdate.setEmail(email);
	    Customer updatedCustomer = customerRepo.save(customerToUpdate);
	    return updatedCustomer;
	    }
	
	
	@Transactional
	public int deleteById (long id) {
		
		Customer customerToDelete = customerRepo.findById(id).orElseThrow(() -> new CustomException("Customer is not found"));
		customerRepo.deleteById(id);
		long cartId =customerToDelete.getCart().getId();
		cartService.deleteCart(cartId);
		
		return 1;
		
	}
	public int countAllCustomers() {
		
		return (int) customerRepo.count();
	}
	public Customer updateImagePath(Customer customer) {

		Customer customerToUpdate = customerRepo.findById(customer.getId()).orElseThrow(() -> new CustomException("Customer is not found"));
	    customerToUpdate.setImagePath(customer.getImagePath());
	   
	    return customerRepo.save(customerToUpdate);
	    }
	
	


}
