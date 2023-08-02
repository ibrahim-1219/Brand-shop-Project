package com.global.shop.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(name = "Cart Entity")
@Entity
@Table(name = "carts")
@Setter
@Getter 
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name = "cart_products", 
			   joinColumns = @JoinColumn(name = "cart_id"), 
			   inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List <Product> products;
	
	@Transient
	private Double totalPrice;
		
	@CreatedBy
	private String createdBy;
	@CreatedDate
	private LocalDateTime createdDateTime;
	@LastModifiedBy
	private String lastModifiedBy;
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

	
	 public Double getTotalPrice() {
	        if (products == null || products.isEmpty()) {
	            return 0.0;
	        }
	        double total = 0.0;
	        for (Product product : products) {
	            total += product.getPrice();
	        }
	        totalPrice = total; // Update the totalPrice attribute
	        return total;
	    }


}
	
	
	
	
	
	


