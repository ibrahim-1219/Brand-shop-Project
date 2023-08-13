package com.global.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.global.shop.entity.Product;
import com.global.shop.error.CustomException;
import com.global.shop.repository.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo productRepo;

	@Cacheable(value = "product", key = "#id")
	public Product findById(long id) {
		
		Product product = productRepo.findById(id).orElseThrow(() -> new CustomException("product not found"));
	
		return product;
	}
	@Cacheable(value = "product", key = "#root.methodName")
	public  Page<Product> findAll(int pageNo,int pageSize,String sortCol,Boolean isAsc) {
	
		Sort sort = Sort.by(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortCol);
	    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		return productRepo.findAllProducts(pageable);
	}
	@Cacheable(value = "product", key = "#root.methodName")
	public  List<Product> findAll() {
		
		return productRepo.findAll();
	}

	@CacheEvict(value = {"product"} , key ="#root.methodName", allEntries = true)
	public List<Product> insertAll(List<Product> products) {

		return productRepo.saveAll(products);
	}

    public int countAllProducts() {
		
		return (int) productRepo.count();
	}
    @CacheEvict(value = {"product"} , key ="#root.methodName", allEntries = true)
	public Product updateProduct(Product product) {
		
		Product productToUpdate = productRepo.findById(product.getId()).orElseThrow(()-> new CustomException("product not found"));
	    productToUpdate.setName(product.getName());
	    productToUpdate.setPrice(product.getPrice());
	    productToUpdate.setQuantity(product.getQuantity());
	    productToUpdate.setDescription(product.getDescription());
	    Product newProduct = productRepo.save(productToUpdate);
		return newProduct;
	}
    @CacheEvict(value = {"product"} , key ="#root.methodName", allEntries = true)
	public Product createProduct(Product product) {
		
		Product product1 = productRepo.findByNameAndDescription(product.getName(),product.getPrice(),product.getDescription());
		if(product1 !=null) {
		    throw new CustomException("this product is already exists");
		}
		return productRepo.save(product);
	}
    @Cacheable(value = "product", key = "#name")
	public Product findByName(String name) {
		
		Product product = productRepo.findByName(name).orElseThrow(() -> new CustomException("product not found"));
		
		return product;
	}
	@CacheEvict(value = {"product"} , key ="#root.methodName", allEntries = true)
	public int deleteProduct(long id) {
	
		Product productToDelete = productRepo.findById(id).orElseThrow(() -> new CustomException("product not found"));
		return productRepo.deleteById(productToDelete.getId());
	}

	public List<Product> findProductsByName(String name) {
		
	     return productRepo.findByNameContaining(name);
	}



	
}


