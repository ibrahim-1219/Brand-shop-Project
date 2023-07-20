package com.global.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.stereotype.Service;

import com.global.shop.entity.Role;
import com.global.shop.repository.RoleRepo;

@Service
public class RoleService {

	
	@Autowired
	private RoleRepo roleRepo;
	
	
	public Role findRoleByName(String name) {
		
		return roleRepo.findRoleByName(name);
	}
	public List<Role> findAll(){
		
		return roleRepo.findAll();
	}
	
	public List<Role> insertAll(List<Role> roles) {

		return roleRepo.saveAll(roles);
	}
	public Role getUserRole() {
	
		return roleRepo.findRoleByName("USER_ROLE");
	}
	public Role getAdminRole() {
		
		return roleRepo.findRoleByName("ADMIN_ROLE");
	}


}
