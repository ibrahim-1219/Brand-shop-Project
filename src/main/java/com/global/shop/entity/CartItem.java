package com.global.shop.entity;

import javax.persistence.Entity;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart_items")
@Setter
@Getter 
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {



	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "product_id")
	    private Product product;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "cart_id")
	    private Cart cart;
        
	    @NotNull(message = "quantity is mandatory")
	    private int quantity;

	    @Transient
	    private double totalPrice;

		public double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(double totalPrice) {
			this.totalPrice = quantity*product.getPrice();
		}
	    
	    

	}

