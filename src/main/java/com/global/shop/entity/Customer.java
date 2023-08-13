package com.global.shop.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
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

@Schema(name = "customer Entity")
@Entity
@Table(name = "customers")
@Setter
@Getter 
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "name is mandatory")
	private String name;
	
	@Email
	@NotNull(message = "email is mandatory")
	@Column(unique = true)
	private String email;
	
	@NotNull(message ="password is mandatory")
	private String password;
	
	private String imagePath;
	
	@Column(name = "active")
	@NotNull
	@Value("1")
	private boolean active;
	
	@CreatedBy
	private String createdBy;
	@CreatedDate
	private LocalDateTime createdDateTime;
	
	@LastModifiedBy
	private String lastModifiedBy;
	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

	
	@ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.REMOVE})
	@JoinTable(name = "customer_roles",
    joinColumns = @JoinColumn(name = "customer_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"),
    foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT),
    inverseForeignKey =@ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	@OrderColumn(name = "id")
	private Set<Role> roles = new HashSet<>();
	
	 @OneToOne(cascade = CascadeType.REMOVE)
	 @JoinColumn(name="cart_id")
	 private Cart cart;

    
	
	public Customer(long id) {
		super();
		this.id = id;
	}


	public Customer(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public void addRole (Role role) {
		roles.add(role);
	}
	
	
	



	
	
	
	
}
