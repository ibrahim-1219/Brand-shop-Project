package com.global.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Product findById(long id) {
		
		Product product = productRepo.findById(id).orElseThrow(() -> new CustomException("product not found"));
	
		return product;
	}

	public  Page<Product> findAll(int pageNo,int pageSize,String sortCol,Boolean isAsc) {
	
		Sort sort = Sort.by(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortCol);
	    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		return productRepo.findAllProducts(pageable);
	}
	
	public  List<Product> findAll() {
		
		return productRepo.findAll();
	}


	public List<Product> insertAll(List<Product> products) {

		return productRepo.saveAll(products);
	}

    public int countAllProducts() {
		
		return (int) productRepo.count();
	}

	public Product updateProduct(Product product) {
		
		Product productToUpdate = productRepo.findById(product.getId()).orElseThrow(()-> new CustomException("product not found"));
	    productToUpdate.setName(product.getName());
	    productToUpdate.setPrice(product.getPrice());
	    productToUpdate.setQuantity(product.getQuantity());
	    productToUpdate.setDescription(product.getDescription());
	    Product newProduct = productRepo.save(productToUpdate);
		return newProduct;
	}

	public Product createProduct(Product product) {
		
		Product product1 = productRepo.findByNameAndDescription(product.getName(),product.getPrice(),product.getDescription());
		if(product1 !=null) {
		    throw new CustomException("this product is already exists");
		}
		return productRepo.save(product);
	}

	public Product findByName(String name) {
		
		Product product = productRepo.findByName(name).orElseThrow(() -> new CustomException("product not found"));
		
		return product;
	}

	public int deleteProduct(long id) {
	
		Product productToDelete = productRepo.findById(id).orElseThrow(() -> new CustomException("product not found"));
		return productRepo.deleteById(productToDelete.getId());
	}

	public List<Product> findProductsByName(String name) {
		
	     return productRepo.findByNameContaining(name);
	}



	
}


