package com.global.shop.security;


import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.global.shop.entity.Customer;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Component
public class AppUserDetail implements UserDetails{
	
	
	private long id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private boolean active;
	
	private List <GrantedAuthority> authorities;
	
	

	public AppUserDetail() {
		super();
	}
	
	

	public AppUserDetail(Customer user) {
		super();
		this.id=user.getId();
		this.name=user.getName();
		this.email=user.getEmail();
		this.password=user.getPassword();
		this.active= user.isActive();
		
		List <GrantedAuthority> authorities = new ArrayList<>();
	
		 if(!user.getRoles().isEmpty()) {
			 user.getRoles().forEach(role -> {
		        		authorities.add(new SimpleGrantedAuthority(role.getName()));	
	        	});
	        }

		
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}


	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}



	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	
		return true;
	}

	@Override
	public boolean isEnabled() {
	
		return active;
	}



	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String toString() {
		return "AppUserDetail [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
	
	

	
	}


