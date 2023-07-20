package com.global.shop.controller;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.global.shop.entity.Customer;
import com.global.shop.service.CustomerService;
import com.global.shop.error.CustomException;
import com.global.shop.error.CustomResponse;
import com.global.shop.security.JWTResponse;
import com.global.shop.security.TokenUtiles;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
	
	Logger log = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenUtiles tokenUtiles;

	@PostMapping("/login")
	public Object login (@RequestBody Map<String, Object> body) {
		
		
		String email= (String)body.get("email");
		String password= (String)body.get("password");
		
		log.info("Authentication");
		Authentication authentication =null;
		
		try {
			
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		}
			
	 catch (DisabledException dis) {
		throw new CustomException("USER_DISABLED");
	} catch (BadCredentialsException e) {
		throw new CustomException("The request is rejected because the credentials are invalid");
	}
		
		log.info("authentication >> " + authentication.isAuthenticated());
		if (authentication.isAuthenticated()) {
			log.info("authentication >> " + authentication.isAuthenticated());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userDetails = customerService.loadUserByUsername(email);
			Optional<Customer> user = customerService.findByEmail(email);
			String token = tokenUtiles.generateToken(email,true);

			return new CustomResponse(new JWTResponse(token, email, user.get().getEmail(), user.get().getRoles(), "Succeefully logged"));
		}

		return new CustomResponse("INVALID", HttpStatus.BAD_REQUEST.value());
	}

	
	
	@PostMapping("/signup")
	public Object signup(@RequestBody Customer user) {
	 

	     Customer userNew = customerService.insert(user); 
        
	    // Authenticate the user
		log.info("Authentication");
	    Authentication authentication = authenticationManager.authenticate(
	              new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));	    
	    log.info("authentication >> " + authentication.isAuthenticated());
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    
	    // Generate JWT token
	    String token = tokenUtiles.generateToken(user.getEmail(), true);

	    return new CustomResponse(new JWTResponse(token, user.getEmail(), user.getName(), userNew.getRoles(), "Successfully signed up"));
	}

}
